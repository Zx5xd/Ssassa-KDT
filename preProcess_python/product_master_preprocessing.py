import json
import re
from collections import defaultdict
import mysql_crud


name_conv_eng = {
    "washing dryer": "WASHING_DRYER",
    "TV": "TELEVISION",
    "kimchi": "KIMCHI_REFRIGERATOR",
}


def conv_name(cat):
    if name_conv_eng.get(cat):
        return name_conv_eng.get(cat)
    else:
        return cat.upper()

def insert_img_path_to_product_img(products) :

    img_insert_sql = """
                        INSERT INTO product_img (img_path)
                                    VALUES (:img_path)
                    """

    for pd in products :
        print(f"{pd}")
        if pd.get('variants'):
            mysql_crud.crud_insert(img_insert_sql, {"img_path": pd.get('simpleImg')})

            for variant_data in pd.get('variants'):
                print(f"img_insert // simple : {pd.get('simpleImg')} , detail : {variant_data.get('detailImg')}")
                if variant_data.get('detailImg'):
                    mysql_crud.crud_insert(img_insert_sql, {"img_path": variant_data.get('detailImg')})
        else:
            print(f"img_insert // simple : {pd.get('simpleImg')} , detail : {pd.get('detailImg')}")
            if pd.get('simpleImg'):
                mysql_crud.crud_insert(img_insert_sql, {"img_path":pd.get('simpleImg')})
            if pd.get('detailImg'):
                mysql_crud.crud_insert(img_insert_sql, {"img_path":pd.get('detailImg')})


def find_img_path_id(path, product, find):
    df = mysql_crud.crud_select_where('product_img','img_path',path)
    img_id = -1

    if not df.empty:
        img_id = int(df['id'].iloc[0])
        print(f"[ DataFrame 보유 ] \n productName : {product} // path:{path} // type : {find}")
    else:
        print(f"DataFrame 비어 있음\n productName : {product} // path:{path} // type : {find}")

    # print(f"id : {int(img_id['id'].iloc[0])} // img_path : {img_id['img_path'].iloc[0]}")

    return img_id

# Master Data preProcessing
def extract_product_detail_info(products, cat_name):
    product_list = []

    # 카테고리 name - code 매핑 불러오기
    name_to_code = mysql_crud.crud_select("categories").to_dict(orient='records')
    print(f"name_to_code : {name_to_code}")

    # 하위 카테고리 name - code 매핑 불러오기
    ch_cat_name_to_code = mysql_crud.crud_select("child_categories").to_dict(orient='records')
    print(f"name_to_code : {ch_cat_name_to_code}")

    item = ([item for item in name_to_code if item['code'] == conv_name(cat_name)] or
            [item for item in ch_cat_name_to_code if item['code'] == cat_name])
    print(f"item : {item}")

    if item[0].get('category_id'):
        cat_temp = item[0].get('category_id')
        item[0]['category_id'] = item[0].get('id')
        item[0]['id'] = cat_temp

    for pd in products:
        if pd.get('detailImg') :
            simple_img_id = find_img_path_id(pd.get('simpleImg'), pd.get("productName"), "[ detail Info ] simple_img")
            detail_img_id = find_img_path_id(pd.get('detailImg'), pd.get("productName"), "[ detail Info ] detail_img")

            product = {'name': pd.get("productName"), 'simple_img': simple_img_id,
                       'detail_img': detail_img_id, 'price': pd.get("price"),
                       'detail': json.dumps(pd.get("detail"), ensure_ascii=False), 'reg': pd.get("reg"),
                       'category_id': item[0].get('id'), 'detail_category_id': item[0].get('category_id', -1),
                       'default_variant': -1,
                       'amount': 0}

            product_list.append(product)
            print(f"product : {pd}")
        else:
            # print(f"[ Variation Product ] pd : {pd}")
            simple_img_id = find_img_path_id(pd.get('simpleImg'), pd.get("productName"), "[ detail Info // Variation ] simple_img")
            # detail_img_id = find_img_path_id(pd.get('detailImg'), pd.get("productName"), "[ detail Info ] detail_img")

            product = {'name': pd.get("productName"), 'simple_img': simple_img_id,
                       'detail_img': 0, 'price': pd.get("price"),
                       'detail': None, 'reg': pd.get("reg"),
                       'category_id': item[0].get('id'), 'detail_category_id': item[0].get('category_id', -1),
                       'default_variant': -2,
                       'amount': 0}

            product_list.append(product)


    return product_list


def extract_product_variation_info(products):
    variant_list = []
    master_items = mysql_crud.crud_select("product_master").to_dict(orient="records")

    for pd in products:

        if pd.get('variants'):
            find_id = find_img_path_id(pd.get("simpleImg"), pd.get("productName"), "[ variation Info ] simpleImg find_id")
            item = [item for item in master_items if item['name'] == pd.get('productName') and item['simple_img'] == find_id]

            # print(f"variation. {pd}")
            if not item:
                print(f"[!] 매칭되는 master 없음: {pd.get('productName')}, {pd.get('simpleImg')}")
                continue

            simple_img_id = find_img_path_id(pd.get('simpleImg'), pd.get("productName"), "[ variation Info ] simple_img")

            # print(f"{pd.get("variants")}")
            for data in pd.get("variants") :
                # print(f" {pd.get("productName")} : {data}")
                # print(f" name : {data['name']}")

                detail_img_id = find_img_path_id(data['detailImg'], pd.get("productName"), "[ variation Info ]  detail_img")

                variant = {'master_id': item[0].get("id"), 'price': data['price'], 'detail': json.dumps(data['detail'], ensure_ascii=False),
                       'detail_img': detail_img_id, 'name': data['name'], 'simple_img': simple_img_id,
                       'amount': 0}

                variant_list.append(variant)

    return variant_list


# 카테고리명 추출
def extract_category_name(filename: str) -> str:
    match = re.match(r"(pcProducts_|peripherals_)?(.+)\.json", filename)
    return match.group(2) if match else filename




