package boco.models.rental;

import boco.models.profile.Profile;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@Entity
public class Lease {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private boolean isApproved;
    private Long fromDatetime;
    private Long toDatetime;
    private boolean isCompleted;

    @OneToOne(mappedBy = "lease", cascade = CascadeType.ALL, orphanRemoval = true)
    private Review ownerReview;

    @OneToOne(mappedBy = "lease", cascade = CascadeType.ALL, orphanRemoval = true)
    private Review leaseeReview;

    @OneToOne(mappedBy = "lease", cascade = CascadeType.ALL, orphanRemoval = true)
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
