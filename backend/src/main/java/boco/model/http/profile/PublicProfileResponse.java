package boco.model.http.profile;

import boco.model.profile.Profile;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
/**
 * This class represents data of a profile available for everyone. Hence, the response will not include
 * username, passwordHash, ratings, rentals, listings, notifications.
 *
 * The class is used to send a response from a REST controller when receiving a get profile HTTP request.
 *
 * See boco.model.profile.Profile class for documentation of the fields of this class.
 */
@Getter @Setter @NoArgsConstructor
public class PublicProfileResponse {
    private Long id;
    private String email;
    private String description;
    private String displayName;
    private String location;
    private String address;
    private Boolean isVerified;
    private String tlf;
    private Timestamp deactivated;

    /**
     * Constructs a public profile response based on a profile entity
     *
     * @param profile The profile entity
     */
    public PublicProfileResponse(Profile profile) {
        this.id = profile.getId();
        this.email = profile.getEmail();
        this.description = profile.getDescription();
        this.displayName = profile.getDisplayName();
        this.isVerified = profile.getIsVerified();
        this.tlf = profile.getTlf();
        this.deactivated = profile.getDeactivated();
        this.address = profile.getAddress();
        this.location = profile.getLocation();
    }
}
