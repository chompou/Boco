package boco.model.http.profile;

import boco.model.profile.Profile;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter @Setter @NoArgsConstructor
public class PrivateProfileResponse {
    private Long id;
    private String username;
    private String email;
    private String description;
    private String displayName;
    private String location;
    private String address;
    private Boolean isVerified;
    private String tlf;
    private Double ratingListing;
    private Double ratingAsLease;
    private Double ratingAsOwner;
    private Timestamp deactivated;

    public PrivateProfileResponse(Profile profile) {
        this.id = profile.getId();
        this.username = profile.getUsername();
        this.email = profile.getEmail();
        this.description = profile.getDescription();
        this.displayName = profile.getDisplayName();
        this.address = profile.getAddress();
        this.location = profile.getLocation();
        this.isVerified = profile.getIsVerified();
        this.tlf = profile.getTlf();
        this.ratingListing = profile.getRatingListing();
        this.ratingAsLease = profile.getRatingAsLeasee();
        this.ratingAsOwner = profile.getRatingAsOwner();
        this.deactivated = profile.getDeactivated();
    }

}
