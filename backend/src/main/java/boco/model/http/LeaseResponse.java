package boco.model.http;

import boco.model.rental.Lease;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    public LeaseResponse(Lease lease) {
        this.id = lease.getId();
        this.isApproved = lease.getIsApproved();
        this.fromDatetime = lease.getFromDatetime();
        this.toDatetime = lease.getToDatetime();
        this.isCompleted = lease.getIsCompleted();
        if (lease.getOwnerReview() != null) this.ownerReview = new ReviewResponse(lease.getOwnerReview());
        if (lease.getLeaseeReview() != null) this.leaseeReview = new ReviewResponse(lease.getLeaseeReview());
        if (lease.getItemReview() != null) this.itemReview = new ReviewResponse(lease.getItemReview());
        this.profileId = lease.getProfile().getId();
        this.leaseeDisplayName = lease.getProfile().getDisplayName();
        this.ownerId = lease.getOwner().getId();
        this.ownerDisplayName = lease.getOwner().getDisplayName();
        this.listingId = lease.getListing().getId();
        this.itemName = lease.getListing().getName();
    }
}
