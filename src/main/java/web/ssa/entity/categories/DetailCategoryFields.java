package web.ssa.entity.categories;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "DETAIL_CATEGORY_FIELDS")
@Getter
@Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class DetailCategoryFields {
    @Id
    private int id;

    @Column(name = "CATEGORY_ID", nullable = false)
    private int categoryId;

    @Column(name = "DETAIL_CATEGORY_ID", nullable = false)
    private int detailCategoryId;

    @Column(name = "ATTRIBUTE_KEY", nullable = false)
    private String attributeKey;

    @Column(name="DISPLAY_NAME")
    private String displayName;

    @Column(name="DATA_TYPE")
    private String dataType;

    @Column(name = "IS_FILTERABLE")
    private Boolean isFilterable;

    @Column()
    private String unit;

    @Column(name = "DISPLAY_ORDER")
    private int displayOrder;

    @Column(columnDefinition = "text")
    private String tooltip;

    @Column(name = "VALUE_LIST", columnDefinition = "text")
    private String valueList;
}
