package boco.models.http;

import boco.models.profile.Profile;
import boco.models.rental.CategoryType;
import boco.models.rental.Image;
import boco.models.rental.Listing;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Getter @Setter @NoArgsConstructor
public class ListingResponse {
    private Long id;
    private String name;
    private String description;
    private String address;
    private boolean isAvailable;
    private boolean isActive;
    private double price;
    private String priceType;
    private Timestamp lastChanged;
    private double rating;
    private List<CategoryType> categoryTypes;
    private Long profileId;
    private String image;

    public ListingResponse(Listing listing) {
        this.id = listing.getId();
        this.name = listing.getName();
        this.description = listing.getDescription();
        this.address = listing.getAddress();
        this.isAvailable = listing.isAvailable();
        this.isActive = listing.isActive();
        this.price = listing.getPrice();
        this.priceType = listing.getPriceType();
        this.lastChanged = listing.getLastChanged();
        this.rating = listing.getRating();
        this.categoryTypes = listing.getCategoryTypes();
        this.profileId = listing.getProfile().getId();
        try {
            this.image = Base64.getEncoder().encodeToString(listing.getImages().get(0).getImage());
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

    }
}
