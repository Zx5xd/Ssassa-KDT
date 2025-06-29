package web.ssa.dto.pay;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SelectedProductDTO {
    private int productId;
    private int variantId;
    private String productName;
    private int quantity;
    private int price;
}