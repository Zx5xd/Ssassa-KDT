package web.ssa.entity.categories;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "CATEGORIES")
@Getter
@Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Categories {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "cat_gen")
    @SequenceGenerator(name = "cat_gen", sequenceName = "cat_seq", initialValue = 1, allocationSize = 1)
    private int id;

    @Column(nullable = false, length = 100)
    private String code;

    @Column(nullable = false, length = 255)
    private String name;

    @OneToMany(mappedBy = "categoryId", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetailCategories> detailCategories;

    @OneToMany(mappedBy = "categoryFieldId", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CategoryFields> categoriesFields;

    @OneToMany(mappedBy = "categoryDetailFieldId", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetailCategories> detailCategoriesFields;
}