package boco.model.http.profile;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor
public class UpdatePasswordRequest {
    String passwordHash;
    String generatedCode;
}
