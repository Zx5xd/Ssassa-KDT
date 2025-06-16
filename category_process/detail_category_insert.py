import json
import pandas as pd
from sqlalchemy import create_engine
from sqlalchemy import text
from category_process import category_insert_tool

parent_cat = {
    "Keyboard" : "PC_PERIPHERAL",
    "earphone" : "PC_PERIPHERAL",
    "headset" : "PC_PERIPHERAL",
    "monitor" : "PC_PERIPHERAL",
    "Mouse" : "PC_PERIPHERAL",
    "speaker" : "PC_PERIPHERAL",
    "Case" : "PC_COMPONENT",
    "Cooler" : "PC_COMPONENT",
    "CPU" : "PC_COMPONENT",
    "GPU" : "PC_COMPONENT",
    "HDD" : "PC_COMPONENT",
    "Mainboard" : "PC_COMPONENT",
    "Power" : "PC_COMPONENT",
    "RAM" : "PC_COMPONENT",
    "SSD" : "PC_COMPONENT",
    "oval" : "WASHER_DRYER_SET",
    "TLW" : "WASHER_DRYER_SET",
    "detachable" : "WASHER_DRYER_SET",
}

name_conv_eng = {
    "washing dryer": "WASHING_DRYER",
    "TV": "TELEVISION",
    "kimchi": "KIMCHI_REFRIGERATOR",
}

name_conv_kor = {
"Keyboard" : "키보드",
    "earphone" : "이어폰",
    "headset" : "헤드셋",
    "monitor" : "모니터",
    "Mouse" : "마우스",
    "speaker" : "스피커",
    "Case" : "케이스",
    "Cooler" : "쿨러",
    "CPU" : "CPU",
    "GPU" : "그래픽 카드",
    "HDD" : "하드디스크",
    "Mainboard" : "메인보드",
    "Power" : "파워서플라이",
    "RAM" : "메모리카드",
    "SSD" : "SSD",
    "oval" : "oval",
    "TLW" : "TLW",
    "detachable" : "detachable"
}

# DB 연결 (네 정보에 맞게 수정)
engine = create_engine("mysql+pymysql://jstest3:jsp1234@hyproz.myds.me:36000/test3")
conn = engine.connect()

# 카테고리 name - code 매핑 불러오기
df = pd.read_sql("SELECT code, name FROM categories", engine)
name_to_code = dict(zip(df['code'], df['name']))

# JSON 로딩
with open("cleaned_unique_fields(Unit Eidt)2.json", encoding="utf-8") as f:
    all_data = json.load(f)

# 분리 저장
for name, data in all_data.items():
    # print(name.upper())
    if parent_cat.get(name) :

        parent_name = parent_cat.get(name)
        code = name_to_code.get(parent_name.upper())

        # print(name, parent_name, code)

        if code:
            seq = 0
            matches = df[df['code'] == parent_name.upper()]
            for idx in matches.index:
                seq = df.index.get_loc(idx) + 1
            # print(f"cat_id: {seq}, code: {name}, name: {name_conv_kor.get(name)}")

            ## child_categories - id를 찾는 과정
            select_query = text("""
                                    select * from child_categories where category_id = :category_id and code = :code
                                """)
            select_result = conn.execute(select_query, {"category_id":seq, "code":name})
            ch_cat_id = 0
            for cat_id, ch_id, code, code_name in select_result :
               ch_cat_id = ch_id

            # print(f"select Result : {select_result}")

            for key, data2 in data.items():
                data2_unit = category_insert_tool.json_data_unit_value(data2)
                # print(f"Child unit len : {data2_unit.__len__()}")
                print(f"data2_unit // {data2_unit} , name : {name}")
                unit_2 = category_insert_tool.unit_process(data2)
                # print(f"unit_2 // {unit_2}")

                for child_order_value in data2['DISPLAY_ORDER'] :
                    data_type = category_insert_tool.classify_unit_first_process(len(data2['unit']), child_order_value)
                    # print(f"Child // data2_order : {child_order_value} , data2_unit : {unit_2} , data_type : {data_type}")

                    if isinstance(unit_2, dict) :
                        unit_2 = unit_2[child_order_value]

                ## child_categories code
                    key_translate = category_insert_tool.deep_translate(key)
                #
                ## print(f"Child_Categories DB INSERT 必, cat_id: {seq}, code: key_translate, name: {key}")
                ## DB INSERT
                    query = text("""
                                  INSERT INTO child_categories_fields (category_id, category_child_id, display_order, is_filterable, attribute_key, data_type, display_name, tooltip, unit, value_list)
                                  VALUES (:category_id, :category_child_id, :display_order, :is_filterable, :attribute_key, :data_type, :display_name, :tooltip, :unit, :value_list)
                              """)
                    if not data_type in "varchar2":
                        conn.execute(query, {"category_id": seq, "category_child_id": ch_cat_id, "display_order": child_order_value,
                                             "is_filterable": True, "attribute_key":key_translate, "data_type":data_type, "display_name":key, "tooltip":None,
                                             "unit": unit_2, "value_list":None})

        else:
            print(f"[경고] 매칭되지 않은 카테고리 이름: {name}")

    else:

        isConvert_name = ""
        if name_conv_eng.get(name) :
            isConvert_name = name_conv_eng.get(name)
        else :
            isConvert_name = name.upper()


        seq = 0
        matches = df[df['code'] == isConvert_name]
        for idx in matches.index:
            seq = df.index.get_loc(idx) + 1
        # print(f"Not parent_cat :: cat_id: {seq}, code: {isConvert_name}, name: {name}")

        # print(name, data)
        query = text("""
                                                 INSERT INTO category_fields (category_id, display_order, is_filterable, attribute_key, data_type, display_name, tooltip, unit, value_list)
                                                 VALUES (:category_id, :display_order, :is_filterable, :attribute_key, :data_type, :display_name, :tooltip, :unit, :value_list)
                                     """)
        for key, data2 in data.items():
            data2_order = data2['DISPLAY_ORDER']
            data2_unit = category_insert_tool.json_data_unit_value(data2)

            ## child_categories code
            ## key_translate : ATTRIBUTE_KEY / key : DISPLAY_NAME
            key_translate = category_insert_tool.deep_translate(key)


            for order_value in data2_order :
                test = ""
                data_type = category_insert_tool.classify_unit_first_process(len(data2['unit']), order_value)

                # print(f"Parent // data2_order : {order_value} , data2_unit : {data2_unit} , data_type : {data_type}")
                # Child_Categories DB INSERT
                if not data_type in "varchar2":
                    conn.execute(query, {"category_id": seq, "display_order": order_value, "is_filterable": True,
                                         "attribute_key": key_translate, "data_type": data_type, "display_name": key,
                                         "tooltip": None, "unit": data2_unit, "value_list": None})


            # print(
            #     f"Child_Categories DB INSERT 必, category_id: {seq} code : {key_translate} name : {key}")



conn.commit()
conn.close()