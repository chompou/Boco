package boco.model.security;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Login response, created if good login credentials, contains json web token (JWT)
 */
@Getter @Setter @NoArgsConstructor
public class LoginResponse {
    private String JWT; //
    private String userID;

    /**
     * Constructor for a login response
     *
     * @param JWT JSON web token
     * @param userId ID of the user stored in JWT
     */
    public LoginResponse(String JWT, Long userId) {
        this.JWT = JWT;
        this.userID = userId +"";
    }
}
