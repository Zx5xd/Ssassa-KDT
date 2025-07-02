package web.ssa.util;

import java.text.SimpleDateFormat;
import java.text.NumberFormat;
import java.util.Date;
import java.util.Locale;

public class FormatUtil {

    /**
     * 날짜를 "yyyy년 MM월 dd일" 형식으로 포맷
     * 
     * @param date 포맷할 날짜
     * @return 포맷된 날짜 문자열
     */
    public static String formatKoreanDate(Date date) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM");
        return formatter.format(date);
    }

    /**
     * 날짜를 "yyyy년 MM월 dd일 HH:mm" 형식으로 포맷
     * 
     * @param date 포맷할 날짜
     * @return 포맷된 날짜 문자열
     */
    public static String formatKoreanDateTime(Date date) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd HH:mm");
        return formatter.format(date);
    }

    /**
     * 숫자를 한국어 천 단위 콤마 형식으로 포맷
     * 
     * @param number 포맷할 숫자
     * @return 포맷된 숫자 문자열
     */
    public static String formatKoreanNumber(int number) {
        NumberFormat formatter = NumberFormat.getInstance(Locale.KOREA);
        return formatter.format(number);
    }

    /**
     * 가격을 한국어 천 단위 콤마 형식으로 포맷 (원 포함)
     * 
     * @param price 포맷할 가격
     * @return 포맷된 가격 문자열 (예: 1,234,567원)
     */
    public static String formatKoreanPrice(int price) {
        return formatKoreanNumber(price) + "원";
    }

    /**
     * 입력 문자열이 유효한 JSON인지 검사
     * 
     * @param json 검사할 문자열
     * @return 유효하면 true, 아니면 false
     */
    public static boolean isValidJson(String json) {
        if (json == null || json.trim().isEmpty()) {
            return false;
        }
        try {
            new com.fasterxml.jackson.databind.ObjectMapper().readTree(json);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}