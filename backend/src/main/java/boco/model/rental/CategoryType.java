package boco.model.rental;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


/**
 * JPA entity representing a category
 */
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@Entity
public class CategoryType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Unique ID of the category (primary key)
    @Column(unique = true)
    private String name; // Name of the category
}
