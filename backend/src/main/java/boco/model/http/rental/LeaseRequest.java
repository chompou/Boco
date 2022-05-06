package boco.model.http.rental;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * This class represents a lease when sending an HTTP POST request to a REST controller.
 *
 * See boco.model.rental.Lease class for documentation of the fields of this class.
 */
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class LeaseRequest {
    private Long fromDatetime;
    private Long toDatetime;
    private Long id;
}
