package web.ssa.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "PRODUCT_IMG")
@Getter
@Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ProductImg {
    @Id
    private int id;

    @Column(name = "IMG_PATH", nullable = false)
    private String imgPath;
}
