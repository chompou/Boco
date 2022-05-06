package boco.model.http.rental;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class ListingResultsResponse {
    private List<ListingResponse> listingResponses;
    private int totalListings;

}
