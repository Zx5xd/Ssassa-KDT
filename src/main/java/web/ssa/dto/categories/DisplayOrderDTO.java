package web.ssa.dto.categories;

import lombok.Data;

@Data
public class DisplayOrderDTO {
    int categoryId;
    Integer childId;
    String attributeKey;
    int oldOrder;
    int newOrder;

    public DisplayOrderDTO(int categoryId, Integer childId, String attributeKey, int oldOrder, int newOrder) {
        this.categoryId = categoryId;
        this.childId = (childId == -1) ? null : childId;
        this.attributeKey = attributeKey;
        this.oldOrder = oldOrder;
        this.newOrder = newOrder;
    }
}
