package boco.models.http;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class LeaseRequest {
    private Timestamp fromDatetime;
    private Timestamp toDatetime;
    private Long listingId;
}
