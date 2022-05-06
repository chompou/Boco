package boco.model.http.profile;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * This class represents an update password request.
 * The class is used to send a response from a REST controller when receiving an update profile HTTP request.
 */
@Getter @Setter @AllArgsConstructor
public class UpdatePasswordRequest {
    String passwordHash;
    String generatedCode;
}
