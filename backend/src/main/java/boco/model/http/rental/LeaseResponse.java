package boco.model.http.rental;

import boco.model.rental.Lease;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * This class represents data of a lease. The class is used to send a response from a REST controller when
 * receiving lease related requests.
 *
 * See boco.model.rental.Lease class for documentation of the fields of this class.
 */
@Getter @Setter @NoArgsConstructor
public class LeaseResponse {
    private Long id;
    private Boolean isApproved;
    private Long fromDatetime;
    private Long toDatetime;
    private Boolean isCompleted;
    private ReviewResponse ownerReview;
    private ReviewResponse leaseeReview;
    private ReviewResponse itemReview;
    private Long profileId;
    private String leaseeDisplayName;
    private Long ownerId;
    private String ownerDisplayName;
    private Long listingId;
    private String itemName;

    /**
     * Creates a leaseResponse from a Lease
     * @param lease The lease who's data is being returned
     */
    public LeaseResponse(Lease lease) {
        this.id = lease.getId();
        this.isApproved = lease.getIsApproved();
        this.fromDatetime = lease.getFromDatetime();
        this.toDatetime = lease.getToDatetime();
        this.isCompleted = lease.getIsCompleted();
        if (lease.getOwnerReview() != null) this.ownerReview = new ReviewResponse(lease.getOwnerReview());
        if (lease.getLeaseeReview() != null) this.leaseeReview = new ReviewResponse(lease.getLeaseeReview());
        if (lease.getItemReview() != null) this.itemReview = new ReviewResponse(lease.getItemReview());
        if (lease.getProfile() != null) this.profileId = lease.getProfile().getId();
        if (lease.getProfile() != null) this.leaseeDisplayName = lease.getProfile().getDisplayName();
        if (lease.getOwner() != null) this.ownerId = lease.getOwner().getId();
        if (lease.getOwner() != null) this.ownerDisplayName = lease.getOwner().getDisplayName();
        if (lease.getListing() != null) this.listingId = lease.getListing().getId();
        if (lease.getListing() != null) this.itemName = lease.getListing().getName();
    }
}
