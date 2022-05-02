package boco.service.security;

import boco.component.BocoHasher;
import boco.models.profile.Profile;
import boco.models.security.LoginRequest;
import boco.models.security.LoginResponse;
import boco.models.security.ProfileDetails;
import boco.service.profile.ProfileService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;

import java.security.NoSuchAlgorithmException;

import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
class LoginServiceTest {

    @InjectMocks
    LoginService loginService;

    @Mock
    private Logger logger;
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private ProfileDetailsService profileDetailsService;
    @Mock
    private ProfileService profileService;
    @Mock
    private JwtUtil jwtUtil;
    @Mock
    private AuthenticationException authenticationException;

    @BeforeEach
    private void setUp() throws NoSuchAlgorithmException {
        Profile profile1 = new Profile();
        profile1.setId(1L);
        profile1.setUsername("profile1");
        profile1.setPasswordHash(BocoHasher.encode("password"));
        profile1.setEmail("1@test.no");
        Profile profile2 = new Profile();
        profile2.setId(2L);
        profile2.setUsername("profile2");
        profile2.setPasswordHash(BocoHasher.encode("anotherPassword"));
        profile2.setEmail("2@test.no");

        lenient().when(authenticationManager.authenticate(new UsernamePasswordAuthenticationToken("unknownProfile", "unknownPassword"))).thenThrow(authenticationException);
        lenient().when(profileDetailsService.loadUserByUsername("profile1")).thenReturn(new ProfileDetails(profile1));
        lenient().when(profileDetailsService.loadUserByUsername("profile2")).thenReturn(new ProfileDetails(profile2));
        lenient().when(profileService.getProfileIdByUsername("profile1")).thenReturn(1L);
        lenient().when(profileService.getProfileIdByUsername("1@test.no")).thenReturn(1L);
        lenient().when(profileService.getProfileIdByUsername("profile2")).thenReturn(2L);
        lenient().when(profileService.getProfileIdByUsername("2@test.no")).thenReturn(2L);
    }

    @Test
    void verifyAndCreateToken() throws Exception {
        LoginRequest login1 = new LoginRequest("profile1", "password");
        LoginRequest login2 = new LoginRequest("profile2", "anotherPassword");
        ResponseEntity<?> loginResponse1 = loginService.verifyAndCreateToken(login1);
        ResponseEntity<?> loginResponse2 = loginService.verifyAndCreateToken(login2);

        assertEquals(200, loginResponse1.getStatusCodeValue());
        assertEquals(200, loginResponse2.getStatusCodeValue());
        assertTrue(loginResponse1.getBody() instanceof LoginResponse && loginResponse2.getBody() instanceof LoginResponse);
    }

    @Test
    void throwsExceptionForInvalidUserAndReturnsForbidden() throws Exception {
        LoginRequest login = new LoginRequest("unknownProfile", "unknownPassword");

        assertThrows(Exception.class, () -> {
            ResponseEntity<?> thrown = loginService.verifyAndCreateToken(login);
            assertEquals(403, thrown.getStatusCodeValue());
        });
    }
}