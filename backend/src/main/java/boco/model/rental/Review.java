package boco.model.rental;

import lombok.*;

import javax.persistence.*;

/**
 * JPA entity representing a listing (item)
 */
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@Entity
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Unique ID of review (primary key)
    private Double rating; // Rating given in the review
    private String comment; // Comment associated with the review. "The review itself".

    @OneToOne
    private Lease lease; // Lease the review belongs to

    /** Constructor of review with the fields rating and comment */
    public Review(Double rating, String comment) {
        this.rating = rating;
        this.comment = comment;
    }
}
