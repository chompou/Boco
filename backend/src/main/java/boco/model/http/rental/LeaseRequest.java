package boco.model.http.rental;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class LeaseRequest {
    private Long fromDatetime;
    private Long toDatetime;
    private Long id;
}
