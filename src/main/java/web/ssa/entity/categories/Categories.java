package web.ssa.entity.categories;

import jakarta.persistence.*;
import lombok.*;
import web.ssa.enumf.CategoryType;

import java.util.List;

@Entity
@Table(name = "CATEGORIES")
@Getter
@Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Categories {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 100)
    private CategoryType code;

    @Column(nullable = false, length = 255)
    private String name;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CategoriesChild> categoryChildren;

    @OneToMany(mappedBy = "categoryFieldId", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CategoryFields> categoriesFields;
}