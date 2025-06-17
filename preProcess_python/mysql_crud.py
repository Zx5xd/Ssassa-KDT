import pandas as pd
from sqlalchemy import create_engine
from sqlalchemy import text

# DB 연결 (네 정보에 맞게 수정)
engine = create_engine("mysql+pymysql://jstest3:jsp1234@hyproz.myds.me:36000/test3")
# conn = engine.connect()

def crud_select(db_name) :
    return pd.read_sql(f"SELECT * FROM {db_name}", engine)

def crud_insert(sql, obj) :
    with engine.begin() as conn:  # 트랜잭션 자동 관리
        insert_query = text(sql)
        conn.execute(insert_query, obj)

