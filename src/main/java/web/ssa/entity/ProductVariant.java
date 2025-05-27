package web.ssa.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Map;

@Entity
@Table(name = "PRODUCT_VARIANT")
@Getter
@Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ProductVariant {
    @Id
    private int id;

    @Column(name = "MASTER_ID", nullable = false)
    private int masterId;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private String name;

    @Column(name="SIMPLE_IMG")
    private String simpleImg;

    @Column(name = "DETAIL_IMG")
    private String detailImg;

    @Column(columnDefinition = "json", nullable = false)
    private String detail;
}
