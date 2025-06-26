import json
from pathlib import Path
import re
from collections import defaultdict

# 브랜드 정규화 맵
brand_aliases = {
    "웨스턴디지털": "Western Digital",
    "웨스턴 디지털": "Western Digital",
    "WesternDigital": "Western Digital",
    "WD": "Western Digital",
    "WD_BLACK": "Western Digital",
    "WD Black": "Western Digital",
    "삼성전자": "삼성",
    "LG전자": "LG",
    "INTEL": "인텔",
    "intel": "인텔",
    "Intel": "인텔",
    "AMD": "AMD",
    "마이크론": "Micron",
    "Micron": "Micron",
    "G.SKILL": "G.SKILL",
    "Gskill": "G.SKILL"
}

# 값과 단위 분리
def split_value_and_unit(s):
    match = re.match(r"^(\d+(?:\.\d+)?)([A-Za-z%Ω]+)$", s)
    if match:
        return match.group(1), match.group(2)
    return s, None

# 정규화
def normalize_value(key, value):
    if not value:
        return "N/A", None

    if isinstance(value, str):
        value = value.strip()
        if not value:
            return "N/A", None

        # 1. 날짜 정규화
        if "등록년월" in key or "출시일" in key:
            value = re.sub(r"년\s*", "년 ", value)
            value = re.sub(r"([1-4])분기", r"Q\1", value)

        # 2. 숫자 단위 정리
        if re.fullmatch(r"\d+개", value):
            value = value.replace("개", "")
        elif re.fullmatch(r"\d+(\.\d+)?V", value):
            value = value.replace("V", "")

        # 3. 제조회사
        if key == "제조회사":
            tokens = value.split()
            if tokens:
                brand_candidate = tokens[0]
                value = brand_aliases.get(brand_candidate, brand_candidate)
            else:
                return "N/A", None

        # 4. value에 정격파워 포함 시 정제
        if "정격파워" in value:
            match = re.search(r"(\d+W)", value)
            if match:
                value = match.group(1)

        # 5. 내장그래픽:탑재 → 탑재
        if value.startswith("내장그래픽:"):
            value = value.replace("내장그래픽:", "")

        # 6. 값과 단위 분리
        val, unit = split_value_and_unit(value)
        if unit:
            return val, unit
        else:
            return value, None  # 단위 없으면 원래 값 유지

    return value, None

# 기본 데이터 추출
def extract_base_details(product_list):
    base_details = []
    for product in product_list:
        variants = product.get("variants")
        if variants:
            seen = set()
            for variant in variants:
                base = variant.get("detail", {}).get("기본")
                if base:
                    frozen = frozenset(base.items())
                    if frozen not in seen:
                        seen.add(frozen)
                        base_details.append(base)
        else:
            base = product.get("detail", {}).get("기본")
            if base:
                base_details.append(base)
    return base_details

# 전처리 수집
def collect_normalized_values(base_details):
    field_values = defaultdict(set)
    unit_map = defaultdict(set)

    for base in base_details:
        for key, value in base.items():
            norm_val, unit = normalize_value(key, value)
            field_values[key].add(norm_val)
            if unit:
                unit_map[key].add(unit)


    final_data = {}
    for key in field_values:
        print(f"key : {field_values[key]}")
        final_data[key] = {
            "value": sorted(field_values[key]),
            "unit": sorted(unit_map[key]) if unit_map[key] else []
        }
    return final_data

# 카테고리명 추출
def extract_category_name(filename: str) -> str:
    # match = re.match(r"(pcProducts_|peripherals_|product_)?(.+)\.json", filename)
    match = re.match(r"(pcProducts_|peripherals_|product_)?(.+)\.json", filename)
    # re_name = match.group(2) if match else filename
    # if '_' in re_name:
    #     re_names = re_name.split('_')
    #     re_name = re_names[0]
    return match.group(2) if match else filename

# 전체 파일 처리
def process_all_files(folder_path: str, output_file: str):
    data_folder = Path(folder_path)
    json_files = list(data_folder.glob("*.json"))
    all_processed = {}

    for file in json_files:
        print(f"{extract_category_name(file.name)}")
        category_name = extract_category_name(file.name)
        with open(file, "r", encoding="utf-8") as f:
            try:
                products = json.load(f)
                if isinstance(products, list):
                    base_details = extract_base_details(products)
                    all_processed[category_name] = collect_normalized_values(base_details)
            except Exception as e:
                print(f"[!] {file.name} 처리 중 오류 발생: {e}")

    with open(output_file, "w", encoding="utf-8") as f:
        json.dump(all_processed, f, ensure_ascii=False, indent=2)

    return output_file

process_all_files("../data", "final_cleaned_unique_fields(unit_value_edit).json")
