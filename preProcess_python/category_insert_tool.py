from deep_translator import GoogleTranslator

# 번역기 준비
translator = GoogleTranslator(source='auto', target='en')


def deep_translate(value) :
    return translator.translate(value)

def classify_value(value: str) -> str | None:
    try:
        if isinstance(int(value), int) :
            # print(f"classify_int, {value}")
            return "number"
    except ValueError:
        try:
            if isinstance(float(value), float) :
                # print(f"classify_float, {value}")
                return "float"
        except ValueError:
            # print(f"classify_str, {value}")
            return "varchar2"

def classify_unit_first_process(unit_len, value) :
    match unit_len:
        case 0 :
            return "varchar2"
        case 1 :
            return classify_value(value)
        case 2 :
            return "number"

def json_data_unit_value(data2) :
    data2_unit = data2['unit']

    if not data2_unit:
        data2_unit = ['None']

    for u in data2_unit :
        return u

def unit_process(data2) :
    if len(data2['unit']) == 2 :
        return unit_len_2(data2)
    else :
        return json_data_unit_value(data2)

def unit_len_2(data2) :
    value_unit = {}

    for val in data2['value']:
        try:
            num = int(val)
            if num >= 100:
                value_unit[val] = "GB"
            else:
                value_unit[val] = "TB"
        except ValueError:
            value_unit[val] = "unkwon"  # 숫자 아닌 값 처리용

    return value_unit