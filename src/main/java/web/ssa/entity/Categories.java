package web.ssa.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "CATEGORIES")
@Getter
@Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Categories {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, length = 100)
    private String code;

    @Column(nullable = false, length = 255)
    private String name;
}