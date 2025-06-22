import json
from collections import defaultdict

# JSON 파일 로드
data = json.load(open('semi_final_cleaned_unique_fields(unit_value_edit).json', 'r', encoding='utf-8'))

# 카테고리 이름 -> ID 매핑
temp_category_ids = {}
category_counter = 1

category_map = defaultdict(list)
for item in data:
    category_name = item.get('category')
    if category_name not in temp_category_ids:
        temp_category_ids[category_name] = category_counter
        category_counter += 1
    category_id = temp_category_ids[category_name]
    category_map[category_id].append(item)

result = []
field_id = 1

for category_id, items in category_map.items():
    attribute_value_map = defaultdict(set)  # 필드별 값 수집
    for item in items:
        attributes = item.get('attributes', {})
        for key, value in attributes.items():
            attribute_value_map[key].add(value)

    display_order = 1
    for key, values in attribute_value_map.items():
        processed_values = []
        sorted_values = sorted(values, key=lambda v: str(v))
        for idx, value in enumerate(sorted_values):
            if isinstance(value, str):
                try:
                    value_num = float(value.replace('GB', '').replace('TB', '').strip())
                    if 'GB' in value:
                        unit = 'GB'
                    elif 'TB' in value:
                        unit = 'TB'
                    else:
                        unit = ''
                    processed_values.append({"value": value_num, "weight": idx + 1})
                    data_type = 'number'
                except:
                    processed_values.append({"value": value, "weight": idx + 1})
                    unit = ''
                    data_type = 'string'
            elif isinstance(value, (int, float)):
                processed_values.append({"value": value, "weight": idx + 1})
                unit = ''
                data_type = 'number'
            else:
                processed_values.append({"value": str(value), "weight": idx + 1})
                unit = ''
                data_type = 'string'

        field = {
            "id": field_id,
            "category_id": category_id,
            "category_child_id": None,
            "attribute_key": key,
            "display_name": key,
            "data_type": data_type,
            "is_filterable": True,
            "unit": unit,
            "display_order": display_order,
            "tooltip": "",
            "value_list": processed_values
        }
        result.append(field)
        field_id += 1
        display_order += 1

with open('category_fields.json', 'w', encoding='utf-8') as f:
    json.dump(result, f, indent=2, ensure_ascii=False)

print(f"{len(result)} fields created and saved to category_fields.json")
