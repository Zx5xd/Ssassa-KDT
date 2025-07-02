package web.ssa.dto.categories;

import java.beans.ConstructorProperties;

import lombok.Data;

@Data
public class DisplayOrderDTO {
    int categoryId;
    Integer childId;
    Integer fieldId;
    // String attributeKey;
    int oldOrder;
    int newOrder;

    @ConstructorProperties({ "id", "categoryId", "categoryChildId", "oldOrder",
            "newOrder" })
    // @ConstructorProperties({ "id", "oldOrder", "newOrder" })
    public DisplayOrderDTO(Integer fieldId, int categoryId, Integer childId,
            // String attributeKey,
            int oldOrder,
            int newOrder) {
        this.categoryId = categoryId;
        this.childId = (childId == -1) ? null : childId;
        this.fieldId = fieldId;
        // this.attributeKey = attributeKey;
        this.oldOrder = oldOrder;
        this.newOrder = newOrder;
    }

    @Override
    public String toString() {
        return "[ DTO ] DisplayOrderDTO{" +
                "fieldId=" + this.fieldId +
                "categoryId=" + this.categoryId +
                ", childId=" + this.childId +
                // ", attributeKey='" + this.attributeKey + '\'' +
                ", oldOrder=" + this.oldOrder +
                ", newOrder=" + this.newOrder +
                '}';
    }
}
