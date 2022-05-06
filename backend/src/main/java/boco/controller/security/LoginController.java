package boco.controller.security;

import boco.model.security.LoginRequest;
import boco.service.security.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


/**
 * Login controller,
 * login endpoints
 */
@RestController
@RequestMapping("/api")
public class LoginController {
    @Autowired
    LoginService loginService;

    /**
     * Responsentity and HTTP response status dependent on user credentials
     *
     * @param loginRequest Json object parsed into LoginRequest
     * @return ResponseEntity with HTTP status code
     * @throws Exception bad credentials entered (returns 403)
     */
    @CrossOrigin
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody LoginRequest loginRequest)throws Exception{
        return loginService.verifyAndCreateToken(loginRequest);
    }

    /**
     * open hello world string for server connection check
     *
     * @return hello world
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String hello(){
        return "hello world";
    }

    @RequestMapping(value = "/my/login", method = RequestMethod.GET)
    public String authHello(){
        return "hello authenticated user";
    }


}
