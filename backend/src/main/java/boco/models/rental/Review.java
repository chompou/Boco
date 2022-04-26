package boco.models.rental;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter @Setter @NoArgsConstructor
@Entity
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double rating;
    private String comment;

    @OneToOne
    private Lease lease;

    public Review(Double rating, String comment) {
        this.rating = rating;
        this.comment = comment;
    }
}
