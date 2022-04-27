package boco.models.rental;

import boco.models.http.LeaseRequest;
import boco.models.profile.Profile;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@Entity
public class Lease {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private boolean isApproved;
    private Timestamp fromDatetime;
    private Timestamp toDatetime;
    private boolean isCompleted;

    @OneToOne(mappedBy = "lease")
    private Review ownerReview;

    @OneToOne(mappedBy = "lease")
    private Review leaseeReview;

    @OneToOne(mappedBy = "lease")
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

    public Lease(Timestamp fromDatetime, Timestamp toDatetime, Profile profile, Listing listing, Profile owner) {
        this.fromDatetime = fromDatetime;
        this.toDatetime = toDatetime;
        this.profile = profile;
        this.listing = listing;
        this.owner = owner;

        this.isApproved = false;
        this.isCompleted = false;
    }
}
