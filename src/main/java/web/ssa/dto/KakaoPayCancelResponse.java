package web.ssa.dto;


import lombok.Data;

@Data
public class KakaoPayCancelResponse {

    private String aid;
    private String tid;
    private String cid;
    private String status;
    private String partner_order_id;
    private String partner_user_id;
    private String payment_method_type;
    private Amount amount;
    private String created_at;
    private String approved_at;
    private String canceled_at;
    private CanceledAmount canceled_amount; // ← 이거 추가!

    @Data
    public static class Amount {
        private int total;
        private int tax_free;
        private int vat;
        private int point;
        private int discount;
    }

    @Data
    public static class CanceledAmount {
        private int total;
        private int tax_free;
        private int vat;
        private int point;
        private int discount;
    }
}