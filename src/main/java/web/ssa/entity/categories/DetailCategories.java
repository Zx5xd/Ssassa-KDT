package web.ssa.entity.categories;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "DETAIL_CATEGORIES")
@Getter @Setter @NoArgsConstructor
@AllArgsConstructor @Builder
public class DetailCategories {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "cat_detail_gen")
    @SequenceGenerator(name = "cat_detail_gen",sequenceName = "cat_detail_seq", initialValue = 1, allocationSize = 1)
    private int id;

//    @Column(name = "CATEGORY_ID", nullable = false)
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Categories categoryId;

    @Column(nullable = false)
    private String code;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "detailCategoryId")
    private List<DetailCategoryFields> detailCategoryFields;
}

