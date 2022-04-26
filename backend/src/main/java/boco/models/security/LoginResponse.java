package boco.models.security;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Login response, created if good login credentlials, contains java web token
 */
@Getter @Setter @NoArgsConstructor
public class LoginResponse {
    private String JWT;
    private String userID;

    /**
     * Default contructor with all params
     *
     * @param JWT the java wrb token
     */
    public LoginResponse(String JWT) {
        this.JWT = JWT;
    }
    public LoginResponse(String JWT, Long userId) {
        this.JWT = JWT;
        this.userID = userId +"";
    }

    public LoginResponse(String JWT, int userId) {
        this.JWT = JWT;
        this.userID = userId +"";
    }

    public LoginResponse(String JWT, String userId) {
        this.JWT = JWT;
        this.userID = userId;
    }
}
