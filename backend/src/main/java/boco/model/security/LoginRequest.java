package boco.model.security;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The type Login request parsed from Json object
 */
@Getter @Setter @NoArgsConstructor
public class LoginRequest {
    private String username;
    private String password;

    /**
     * Default constructor with all params
     *
     * @param username username
     * @param password password
     */
    public LoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
