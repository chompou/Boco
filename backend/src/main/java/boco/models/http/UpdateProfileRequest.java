package boco.models.http;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class UpdateProfileRequest {
    private String email;
    private String description;
    private String displayName;
    private String passwordHash;
    private String address;
    private String tlf;
    private Boolean isPersonal;
}
