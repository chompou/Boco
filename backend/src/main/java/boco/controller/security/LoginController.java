package boco.controller.security;

import boco.models.security.LoginRequest;
import boco.models.security.LoginResponse;
import boco.service.security.JwtUtil;
import boco.service.security.ProfileDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;


/**
 * Login controller,
 * login endpoints
 */
@RestController
@RequestMapping("/api")
public class LoginController {
    private Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private ProfileDetailsService profileDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

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
        try {authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
        );
        }catch (BadCredentialsException e){
            logger.info("bad login request denied");
        }
        final UserDetails userDetails = profileDetailsService.loadUserByUsername(loginRequest.getUsername());
        if (userDetails.getPassword().equals(loginRequest.getPassword())){
            final String jwt = jwtUtil.generateToken(userDetails);
            return ResponseEntity.ok(new LoginResponse(jwt));
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
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
