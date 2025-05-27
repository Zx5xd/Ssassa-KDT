package web.ssa.entity;

import jakarta.persistence.*;
import lombok.*;
import web.ssa.dto.ProductVariantDTO;

import java.util.Date;
import java.util.List;


@Entity
@Table(name = "PRODUCT_MASTER")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductMaster {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, length = 255)
    private String name;

    @Column(name = "CATEOGRY_ID", nullable = false)
    private int categoryId;

    @Column(name = "DETAIL_CATEGORY_ID", nullable = false)
    private int detailCategoryId;

    @Column(name = "SIMPLE_IMG")
    private int simpleImg;

    @Column(name = "DETAIL_IMG")
    private int detailImg;

    @Column()
    private int price;

    @Column(columnDefinition = "json")
    private String detail;

    @Column(name = "DEFAULT_VARIANT")
    int defaultVariant;

    @Column(nullable = false)
    private Date reg;
}
