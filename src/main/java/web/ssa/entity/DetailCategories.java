package web.ssa.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "DETAIL_CATEGORIES")
@Getter @Setter @NoArgsConstructor
@AllArgsConstructor @Builder
public class DetailCategories {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "CATEGORY_ID", nullable = false)
    private int categoryId;

    @Column(nullable = false)
    private String code;

    @Column(nullable = false)
    private String name;
}

