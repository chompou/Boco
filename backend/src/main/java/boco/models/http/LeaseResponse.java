package boco.models.http;

import boco.models.rental.Lease;
import boco.models.rental.Review;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter @Setter @NoArgsConstructor
public class LeaseResponse {
    private Long id;
    private boolean isApproved;
    private Timestamp fromDatetime;
    private Timestamp toDatetime;
    private boolean isCompleted;
    private Review ownerReview;
    private Review leaseeReview;
    private Review itemReview;
    private Long profileId;
    private Long listingId;

    public LeaseResponse(Lease lease) {
        this.id = lease.getId();
        this.isApproved = lease.isApproved();
        this.fromDatetime = lease.getFromDatetime();
        this.toDatetime = lease.getToDatetime();
        this.isCompleted = lease.isCompleted();
        this.ownerReview = lease.getOwnerReview();
        this.leaseeReview = lease.getLeaseeReview();
        this.itemReview = lease.getItemReview();
        this.profileId = lease.getProfile().getId();
        this.listingId = lease.getListing().getId();
    }
}
