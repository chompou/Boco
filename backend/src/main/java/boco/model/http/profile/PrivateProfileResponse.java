package boco.model.http.profile;

import boco.model.profile.Profile;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

/**
 * This class represents data of a profile available for the profile itself. PasswordHash is never included.
 * The class is used to send a response from a REST controller when receiving a get profile HTTP request.
 *
 * See boco.model.profile.Profile class for documentation of the fields of this class.
 */
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

    /**
     * Constructs a private profile response based on a profile entity
     *
     * @param profile The profile entity
     */
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
