package boco.models.http;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class LeaseRequest {
    private String fromDatetime; // format: yyyy-[m]m-[d]d hh:mm:ss[.f...]
    private String toDatetime; // format: yyyy-[m]m-[d]d hh:mm:ss[.f...]
    private Long listingId;
}
