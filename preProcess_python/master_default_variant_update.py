import product_master_preprocessing
import mysql_crud

# [ product_master ] default_variant 값 삽입
variant_dict = product_master_preprocessing.export_variant_info_to_update()

default_variant_update = """
                        UPDATE product_master
                        SET default_variant = :default_variant
                        WHERE id = :master_id; 
                        """



for vd in variant_dict:
    # print(f"vd : {vd} // variant_dict[vd] : {variant_dict[vd][0]}")
    sql_obj = {
        "default_variant": variant_dict[vd][0],
        "master_id":vd
    }
    # print(f"sql_obj : {sql_obj}\n\n")
    mysql_crud.crud_insert(default_variant_update, sql_obj)