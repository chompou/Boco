package boco.service.security;

import boco.model.profile.Profile;
import boco.model.security.ProfileDetails;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class JwtUtilTest {
    private ProfileDetails profileDetails;

    @InjectMocks
    JwtUtil jwtUtil;

    @BeforeEach
    public void setup(){
        Profile testProfile = new Profile();
        testProfile.setUsername("test");
        testProfile.setPasswordHash("password");
        this.profileDetails = new ProfileDetails(testProfile);
    }

    @Test
    void generatedTokenNotNull() {
        String token = jwtUtil.generateToken(profileDetails);
        assertNotNull(token);
    }

    @Test
    void generatedTokenNotNullAtEmptyUser() {
        String token = jwtUtil.generateToken(new ProfileDetails(new Profile()));
        assertNotNull(token);
    }

    @Test
    void generatedTokenIsCorrectForUser() {
        String token = jwtUtil.generateToken(profileDetails);
        assertTrue(jwtUtil.validateToken(token, profileDetails));
    }

    @Test
    void generatedTokenIsWrongForDifferentUser() {
        String token = jwtUtil.generateToken(profileDetails);
        assertFalse(jwtUtil.validateToken(token, new ProfileDetails(new Profile())));
    }

    @Test
    void extractExpirationAssertBetweenNowAnd24Hours() {
        String token = jwtUtil.generateToken(profileDetails);
        assertTrue(jwtUtil.extractExpiration(token).after(new Date()));
        assertTrue(jwtUtil.extractExpiration(token).before(new Date(System.currentTimeMillis()+1000*60*60*24)));
    }

    @Test
    void extractUsername() {
        String token = jwtUtil.generateToken(profileDetails);
        String username = jwtUtil.extractUsername(token);
        assertEquals(username, profileDetails.getUsername());
    }

    @Test
    void extractUsernameFailOnErrorUser() {
        String token = jwtUtil.generateToken(new ProfileDetails(new Profile()));
        String username = jwtUtil.extractUsername(token);
        assertNull(username);
    }
}