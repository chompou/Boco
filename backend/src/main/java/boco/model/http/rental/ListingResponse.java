package boco.model.http.rental;

import boco.model.rental.CategoryType;
import boco.model.rental.Listing;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.Base64;
import java.util.List;

/**
 * This class represents the data of a listing. The class is used to send a response from a REST controller when
 * receiving listing related requests.
 *
 * See boco.model.rental.Listing class for documentation of the fields of this class.
 */
@Getter @Setter @NoArgsConstructor
public class ListingResponse {
    private Long id;
    private String name;
    private String description;
    private String address;
    private String location;
    private Boolean isActive;
    private Double price;
    private String priceType;
    private Timestamp lastChanged;
    private Double rating;
    private List<CategoryType> categoryTypes;
    private Long profileId;
    private String image;
    private Double distance;

    /**
     * Creates an listingResponse from a listing object
     * @param listing the listing we are creating it from
     */
    public ListingResponse(Listing listing) {
        this.id = listing.getId();
        this.name = listing.getName();
        this.description = listing.getDescription();
        this.address = listing.getProfile().getAddress();
        this.location = listing.getProfile().getLocation();
        this.isActive = listing.getIsActive();
        this.price = listing.getPrice();
        this.priceType = listing.getPriceType();
        this.lastChanged = listing.getLastChanged();
        if (listing.getRating() != null){
            this.rating = listing.getRating();
        }else{
            this.rating = -1.0;
        }
        this.categoryTypes = listing.getCategoryTypes();
        this.profileId = listing.getProfile().getId();
        this.distance = -1.0;
        try {
            if (!listing.getImages().isEmpty()) {
                this.image = Base64.getEncoder().encodeToString(listing.getImages().get(0).getImage());
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    /**
     * Creates a listing response from a listing, with distance set.
     * @param listing The listing response data is gotten from.
     * @param distance The distance to be set
     */
    public ListingResponse(Listing listing, double distance){
        this.id = listing.getId();
        this.name = listing.getName();
        this.description = listing.getDescription();
        this.isActive = listing.getIsActive();
        this.price = listing.getPrice();
        this.address = listing.getProfile().getAddress();
        this.location = listing.getProfile().getLocation();
        this.priceType = listing.getPriceType();
        this.lastChanged = listing.getLastChanged();
        this.rating = listing.getRating();
        this.categoryTypes = listing.getCategoryTypes();
        this.profileId = listing.getProfile().getId();
        try {
            if (!listing.getImages().isEmpty()) {
                this.image = Base64.getEncoder().encodeToString(listing.getImages().get(0).getImage());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        this.distance =  distance;

    }
}
