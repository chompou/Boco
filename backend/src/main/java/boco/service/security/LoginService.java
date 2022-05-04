package boco.service.security;

import boco.component.BocoHasher;
import boco.controller.security.LoginController;
import boco.model.security.LoginRequest;
import boco.model.security.LoginResponse;
import boco.service.profile.ProfileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
    private final Logger logger;
    private final AuthenticationManager authenticationManager;
    private final ProfileDetailsService profileDetailsService;
    private final ProfileService profileService;
    private final JwtUtil jwtUtil;

    @Autowired
    public LoginService(AuthenticationManager authenticationManager, ProfileDetailsService profileDetailsService, ProfileService profileService, JwtUtil jwtUtil) {
        this.logger = LoggerFactory.getLogger(LoginController.class);
        this.authenticationManager = authenticationManager;
        this.profileDetailsService = profileDetailsService;
        this.profileService = profileService;
        this.jwtUtil = jwtUtil;
    }

    public ResponseEntity<?> verifyAndCreateToken(LoginRequest loginRequest)throws Exception{
        try {authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), BocoHasher.encode(loginRequest.getPassword()))
        );
        }catch (BadCredentialsException e){
            logger.info("bad login request denied");
        }
        final UserDetails userDetails = profileDetailsService.loadUserByUsername(loginRequest.getUsername());
        if (userDetails.getPassword().equals(BocoHasher.encode(loginRequest.getPassword()))){
            final String jwt = jwtUtil.generateToken(userDetails);
            return ResponseEntity.ok(new LoginResponse(jwt, profileService.getProfileIdByUsername(jwtUtil.extractUsername(jwt))));
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }
}
