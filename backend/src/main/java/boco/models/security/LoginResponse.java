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

    /**
     * Default contructor with all params
     *
     * @param JWT the java wrb token
     */
    public LoginResponse(String JWT) {
        this.JWT = JWT;
    }
}
