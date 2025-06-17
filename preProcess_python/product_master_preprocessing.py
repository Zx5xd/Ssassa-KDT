
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


# Master Data preProcessing
def extract_product_detail_info(products, cat_name):
    product_list = []

    # 카테고리 name - code 매핑 불러오기
    name_to_code = mysql_crud.crud_select("categories").to_dict(orient='records')

    # 하위 카테고리 name - code 매핑 불러오기
    ch_cat_name_to_code = mysql_crud.crud_select("child_categories").to_dict(orient='records')

    item = ([item for item in name_to_code if item['code'] == conv_name(cat_name)] or
            [item for item in ch_cat_name_to_code if item['code'] == cat_name])

    if item[0].get('category_id'):
        cat_temp = item[0].get('category_id')
        item[0]['category_id'] = item[0].get('id')
        item[0]['id'] = cat_temp

    for pd in products:
        product = {'name': pd.get("productName"), 'simple_img': pd.get("simpleImg"),
                   'detail_img': pd.get("detailImg"), 'price': pd.get("price"),
                   'detail': pd.get("detail"), 'reg': pd.get("reg"),
                   'cateogry_id': item[0].get('id'), 'detail_category_id': item[0].get('category_id', -1),
                   'default_variant': -1,
                   'amount': 0}

        product_list.append(product)

    return product_list


def extract_product_variation_info(products):
    variant_list = []
    master_items = mysql_crud.crud_select("product_master").to_dict(orient="records")

    for pd in products:

        # detail 있는거
        if not pd.get('detail'):
            item = [item for item in master_items if item['name'] == pd.get('productName') and item['simple_img'] == pd.get('simpleImg')]

            # print(f"variation. {pd}")

            # print(f"{pd.get("variants")}")
            for data in pd.get("variants") :
                # print(f" {pd.get("productName")} : {data}")
                # print(f" name : {data['name']}")

                variant = {'master_id': item[0].get("id"), 'price': data['price'], 'detail': data['detail'],
                       'detail_img': data['detailImg'], 'name': data['name'], 'simple_img': pd.get("simpleImg"),
                       'amount': 0}

                variant_list.append(variant)

    return variant_list


# 카테고리명 추출
def extract_category_name(filename: str) -> str:
    match = re.match(r"(pcProducts_|peripherals_)?(.+)\.json", filename)
    return match.group(2) if match else filename




