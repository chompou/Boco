package boco.model.http.rental;

import lombok.*;

/**
 * This class represents a review when sending an HTTP POST request to a REST controller.
 *
 * See boco.model.rental.Review class for documentation of the fields of this class.
 */
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ReviewRequest {
    private Double rating; // Rating 1-5
    private String comment;
    private Long leaseId; // ID of the lease where review should be added
}
