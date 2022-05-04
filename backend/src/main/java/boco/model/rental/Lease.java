package boco.model.rental;

import boco.model.profile.Profile;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@Entity
public class Lease {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Boolean isApproved;
    private Long fromDatetime;
    private Long toDatetime;
    private Boolean isCompleted;

    @OneToOne
    @JoinColumn(
            name = "owner_review_id", // name of the foreign key
            referencedColumnName = "id" // references id in Review entity
    )
    private Review ownerReview;

    @OneToOne
    @JoinColumn(
            name = "leasee_review_id", // name of the foreign key
            referencedColumnName = "id" // references id in Review entity
    )
    private Review leaseeReview;

    @OneToOne
    @JoinColumn(
            name = "item_review_id", // name of the foreign key
            referencedColumnName = "id" // references id in Review entity
    )
    private Review itemReview;

    @ManyToOne
    @JoinColumn(name = "profile_id")
    @JsonManagedReference
    private Profile profile;

    @ManyToOne
    @JoinColumn(name = "listing_id")
    @JsonManagedReference
    private Listing listing;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    @JsonManagedReference
    private Profile owner; // The owner of the listing

    public Lease(Long fromDatetime, Long toDatetime, Profile profile, Listing listing, Profile owner) {
        this.fromDatetime = fromDatetime;
        this.toDatetime = toDatetime;
        this.profile = profile;
        this.listing = listing;
        this.owner = owner;

        this.isApproved = false;
        this.isCompleted = false;
    }
}
