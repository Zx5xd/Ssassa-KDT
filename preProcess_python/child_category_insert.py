import json
import pandas as pd
from sqlalchemy import create_engine
from sqlalchemy import text

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
with open("final_cleaned_unique_fields(unit_value_edit).json", encoding="utf-8") as f:
    all_data = json.load(f)

# 분리 저장
for name, data in all_data.items():
    if parent_cat.get(name) :
        parent_name = parent_cat.get(name)
        code = name_to_code.get(parent_name.upper())

        # print(name, parent_name, code)

        if code:
            seq = 0
            matches = df[df['code'] == parent_name.upper()]
            for idx in matches.index:
                seq = df.index.get_loc(idx) + 1
            print(f"cat_id: {seq}, code: {name}, name: {name_conv_kor.get(name)}")

            query = text("""
                              INSERT INTO child_categories (category_id, code, name)
                              VALUES (:category_id, :code, :name)
                          """)
            conn.execute(query, {"category_id": seq, "code": name, "name": name_conv_kor.get(name)})

        else:
            print(f"[경고] 매칭되지 않은 카테고리 이름: {name}")

    else:
        isConvert_name = ""



conn.commit()
conn.close()