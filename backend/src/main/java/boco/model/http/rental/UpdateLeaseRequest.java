package boco.model.http.rental;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * This class represents updated fields in a lease when sending an HTTP PUT request to a REST controller.
 *
 */
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class UpdateLeaseRequest {
    private Boolean isApproved;
    private Boolean isCompleted;
    private Long leaseId;

}
