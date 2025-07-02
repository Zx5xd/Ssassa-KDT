package web.ssa.entity.categories;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "CHILD_CATEGORIES")
@Getter @Setter @NoArgsConstructor
@AllArgsConstructor @Builder
public class CategoriesChild {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

//    @Column(name = "CATEGORY_ID", nullable = false)
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Categories categoryChildId; // CategoryId

    @Column(nullable = false)
    private String code;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "categoryChildId")
    private List<CategoryFields> categoryFields;
}

