package web.ssa.entity.categories;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "CATEGORY_FIELDS")
@Getter
@Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class CategoryFields {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "CATEGORY_ID")
    private Categories categoryFieldId;

    @Column(name = "ATTRIBUTE_KEY", nullable = false)
    private String attributeKey;

    @Column(name = "DISPLAY_NAME")
    private String displayName;

    @Column(name = "DATA_TYPE")
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
