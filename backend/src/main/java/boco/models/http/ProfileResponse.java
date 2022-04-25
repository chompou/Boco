package boco.models.http;

import boco.models.profile.Profile;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter @Setter @NoArgsConstructor
public class ProfileResponse {
    private Long id;
    private String username;
    private String email;
    private String description;
    private String displayName;
    private String address;
    private Boolean isVerified;
    private String tlf;
    private Double ratingListing;
    private Double ratingProfile;
    private Double ratingGiven;
    private Timestamp deactivated;

    public ProfileResponse(Profile profile) {
        this.id = profile.getId();
        this.username = profile.getUsername();
        this.email = profile.getEmail();
        this.description = profile.getDescription();
        this.displayName = profile.getDisplayName();
        this.address = profile.getAddress();
        this.isVerified = profile.getIsVerified();
        this.tlf = profile.getTlf();
        this.ratingListing = profile.getRatingListing();
        this.ratingProfile = profile.getRatingProfile();
        this.ratingGiven = profile.getRatingGiven();
        this.deactivated = profile.getDeactivated();
    }

}
