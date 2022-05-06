package boco.model.http.profile;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * This class represents a profile when sending an HTTP POST request to a REST controller
 *
 * See boco.model.profile.Profile class for documentation of the fields of this class.
 */
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class ProfileRequest {
    private String username;
    private String email;
    private String description;
    private String displayName;
    private String passwordHash;
    private String address;
    private String location;
    private String tlf;
    private Boolean isPersonal;
}