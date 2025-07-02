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

    @Override
    public String toString() {
        return "[ DTO ] DisplayOrderDTO{" +
                "categoryId=" + this.categoryId +
                ", childId=" + this.childId +
                ", attributeKey='" + this.attributeKey + '\'' +
                ", oldOrder=" + this.oldOrder +
                ", newOrder=" + this.newOrder +
                '}';
    }
}
