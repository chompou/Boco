package boco.model.http.rental;

import boco.model.rental.CategoryType;
import boco.model.rental.Listing;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.Base64;
import java.util.List;

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
            if (listing.getImages().size() != 0) {
                this.image = Base64.getEncoder().encodeToString(listing.getImages().get(0).getImage());
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

    }

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
            if (listing.getImages().size() != 0) {
                this.image = Base64.getEncoder().encodeToString(listing.getImages().get(0).getImage());
            }
        }catch (Exception e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        this.distance =  distance;

    }
}
