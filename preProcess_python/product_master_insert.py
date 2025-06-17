import json
from pathlib import Path
import product_master_preprocessing
import mysql_crud

data_folder = Path("../data")
json_files = list(data_folder.glob("*.json"))
all_processed = {}

for file in json_files:
    category_name = product_master_preprocessing.extract_category_name(file.name)
    with open(file, "r", encoding="utf-8") as f:
        try:
            products = json.load(f)
            if isinstance(products, list):
                master_lists = product_master_preprocessing.extract_product_detail_info(products, category_name)

                master_insert_sql = """
                                    INSERT INTO product_master (name, simple_img, detail_img, price, 
                                                                detail, reg, cateogry_id, detail_category_id, default_variant, amount)
                                    VALUES (:name, :simple_img, :detail_img, :price, 
                                                                :detail, :reg, :cateogry_id, :detail_category_id, :default_variant, :amount)
                                    """

                for data in master_lists:
                    mysql_crud.crud_insert(master_insert_sql, data)

                    variants_lists = product_master_preprocessing.extract_product_variation_info(products)

                if variants_lists:
                    variants_insert_sql = """
                                          INSERT INTO product_variant (master_id, price, detail, detail_img, 
                                                                    name, simple_img, amount)
                                          VALUES (:master_id, :price, :detail, :detail_img, 
                                                                    :name, :simple_img, :amount)  
                                    """

                    for data in variants_lists:
                        mysql_crud.crud_insert(variants_insert_sql, data)


        except Exception as e:
            print(f"[!] {file.name} 처리 중 오류 발생: {e}")
