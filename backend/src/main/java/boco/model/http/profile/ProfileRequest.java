package boco.model.http.profile;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * This class represents a profile when sending a HTTP request to a REST controller
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