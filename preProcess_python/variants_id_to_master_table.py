import mysql_crud

# product_master 테이블에 variants 테이블 내 Primary 키 매핑 후 삽입
db_master_datas = mysql_crud.crud_select("product_master")
db_variants_datas = mysql_crud.crud_select("product_variant")

for master_data in db_master_datas:
    variants_data = [item for item in db_variants_datas if item['master_id'] == master_data['id']]
    # master_data['default_variant'] = variants_data[0]['id']

    if variants_data:
        variantsId_update_sql = """
                        UPDATE product_master SET default_variant = :variant_id WHERE id = :master_id
                            """
        mysql_crud.crud_insert(variantsId_update_sql, {
                                                            "variants_id": variants_data[0]['id'],
                                                            "master_id": master_data['id']})
