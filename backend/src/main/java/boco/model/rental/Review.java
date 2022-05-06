package boco.model.rental;

import lombok.*;

import javax.persistence.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
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
