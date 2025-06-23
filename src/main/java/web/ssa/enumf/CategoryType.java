package web.ssa.enumf;

public enum CategoryType {
    AIR("에어프라이어"),
    DRYER("건조기"),
    WASHER("세탁기"),
    WASHING_DRYER("올인원 세탁기"),
    WASHER_DRYER_SET("세탁+건조기"),
    REFRIGERATOR("냉장고"),
    KIMCHI_REFRIGERATOR("김치냉장고"),
    PC_PERIPHERAL("PC주변기기"), // 헤드셋, 이어폰, 모니터, 키보드, 마우스, 스피커
    PC_COMPONENT("PC부품"), // CPU, GPU, etc
    SMARTPHONE("핸드폰"), // 아이폰, 구글폰 등
    TABLET_PC("테블릿PC"),
    TELEVISION("TV");

    private final String displayName;

    CategoryType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
