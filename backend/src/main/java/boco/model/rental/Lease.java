package boco.model.rental;

import boco.model.profile.Profile;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;

/**
 * JPA entity representing a lease
 */
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@Entity
public class Lease {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // ID of lease (primary key)
    private Boolean isApproved; // Boolean indicating if the lease is approved
    private Long fromDatetime; // Start date of lease as milliseconds since the unix epoch
    private Long toDatetime; // End date of lease on as milliseconds since the unix epoch
    private Boolean isCompleted; // Boolean indicating if the lease is completed

    @OneToOne
    @JoinColumn(
            name = "owner_review_id", // name of the foreign key
            referencedColumnName = "id" // references id in Review entity
    )
    private Review ownerReview; // Review of the owner of the listing in this lease

    @OneToOne
    @JoinColumn(
            name = "leasee_review_id", // name of the foreign key
            referencedColumnName = "id" // references id in Review entity
    )
    private Review leaseeReview; // Review of the leasee of this lease

    @OneToOne
    @JoinColumn(
            name = "item_review_id", // name of the foreign key
            referencedColumnName = "id" // references id in Review entity
    )
    private Review itemReview; // Review of the item of this lease

    @ManyToOne
    @JoinColumn(name = "profile_id")
    @JsonManagedReference
    private Profile profile; // The leasee of the lease

    @ManyToOne
    @JoinColumn(name = "listing_id")
    @JsonManagedReference
    private Listing listing; // The listing (item) the lease is for

    @ManyToOne
    @JoinColumn(name = "owner_id")
    @JsonManagedReference
    private Profile owner; // The owner of the listing of this lease

    /**
     * Constructor of lease. isApproved and isCompleted is set to false by default.
     *
     * @param fromDatetime Start date of lease as milliseconds since the unix epoch
     * @param toDatetime End date of lease as milliseconds since the unix epoch
     * @param profile Profile of the leasee
     * @param listing Listing (item) to create a lease for
     * @param owner Owner of the item to create a lease for
     */
    public Lease(Long fromDatetime, Long toDatetime, Profile profile, Listing listing, Profile owner) {
        this.fromDatetime = fromDatetime;
        this.toDatetime = toDatetime;
        this.profile = profile;
        this.listing = listing;
        this.owner = owner;

        // Setting default values
        this.isApproved = false;
        this.isCompleted = false;
    }
}
