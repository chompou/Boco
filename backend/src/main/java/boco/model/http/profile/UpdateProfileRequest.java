package boco.model.http.profile;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * This class represents new data of a profile to be updated. The class is used to send a response from a REST
 * controller when receiving a get profile HTTP request.
 *
 * See boco.model.profile.Profile class for documentation of the fields of this class.
 */
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class UpdateProfileRequest {
    private String email;
    private String description;
    private String displayName;
    private String passwordHash;
    private String address;
    private String location;
    private String tlf;
}
