package boco.model.http;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class ProfileRequest {
    private String username;
    private String email;
    private String description;
    private String displayName;
    private String passwordHash;
    private String address;
    private String tlf;
    private Boolean isPersonal;
}