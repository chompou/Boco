package boco.model.http.rental;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * This class represents a sublist of listingresponses with a value for number of listings in the main list.
 * This class is used for some response where listingsresults are returned with pagiantion.
 */
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class ListingResultsResponse {
    private List<ListingResponse> listingResponses;
    private int totalListings;

}
