import pandas as pd
from sqlalchemy import create_engine
from sqlalchemy import text

# DB 연결 (네 정보에 맞게 수정)
engine = create_engine("mysql+pymysql://jshop:jsp1234@hyproz.myds.me:36000/shop", pool_pre_ping=True)
# conn = engine.connect()

def crud_select(db_name) :
    return pd.read_sql(f"SELECT * FROM {db_name}", engine)

def crud_select_where(db_name, position, value) :
    query = text(f"SELECT * FROM {db_name} WHERE {position} = :value")
    return pd.read_sql(query, con=engine, params={"value" : value})

def crud_insert(sql, obj) :
    with engine.begin() as conn:  # 트랜잭션 자동 관리
        insert_query = text(sql)
        conn.execute(insert_query, obj)

