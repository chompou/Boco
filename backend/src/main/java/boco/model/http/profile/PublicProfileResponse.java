package boco.model.http.profile;

import boco.model.profile.Profile;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

// Profile without passwordHash, address, rating statistics,

/**
 * Profile entity without the fields: username, passwordHash, address, ratings, rentals, listings, notifications
 */
@Getter @Setter @NoArgsConstructor
public class PublicProfileResponse {
    private Long id;
    private String email;
    private String description;
    private String displayName;
    private Boolean isVerified;
    private String tlf;
    private Timestamp deactivated;

    public PublicProfileResponse(Profile profile) {
        this.id = profile.getId();
        this.email = profile.getEmail();
        this.description = profile.getDescription();
        this.displayName = profile.getDisplayName();
        this.isVerified = profile.getIsVerified();
        this.tlf = profile.getTlf();
        this.deactivated = profile.getDeactivated();
    }
}
