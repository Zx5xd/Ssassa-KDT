package web.ssa.enumf;

import lombok.Getter;

@Getter
public enum CategoryType {
    AIR("에어프라이어", "AIR"),
    DRYER("건조기", "DRYER"),
    WASHER("세탁기", "WASHER"),
    WASHING_DRYER("올인원 세탁기", "WASHING_DRYER"),
    WASHER_DRYER_SET("세탁+건조기", "WASHER_DRYER_SET"),
    REFRIGERATOR("냉장고", "REFRIGERATOR"),
    KIMCHI_REFRIGERATOR("김치냉장고", "KIMCHI_REFRIGERATOR"),
    PC_PERIPHERAL("PC주변기기", "PC_PERIPHERAL"), // 헤드셋, 이어폰, 모니터, 키보드, 마우스, 스피커
    PC_COMPONENT("PC부품", "PC_COMPONENT"), // CPU, GPU, etc
    SMARTPHONE("핸드폰", "PHONE"), // 아이폰, 구글폰 등
    TABLET_PC("테블릿PC", "TABLET_PC"),
    LAPTOP("노트북","LAPTOP"),
    TELEVISION("TV", "TELEVISION");

    private final String displayName;
    private final String code;

    CategoryType(String displayName, String code) {
        this.displayName = displayName;
        this.code = code;
    }

}
