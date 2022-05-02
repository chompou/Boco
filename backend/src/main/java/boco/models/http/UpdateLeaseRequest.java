package boco.models.http;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class UpdateLeaseRequest {
    private Boolean isApproved;
    private Boolean isCompleted;
    private Long leaseId;

}
