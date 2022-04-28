package boco.service.security;

import boco.models.profile.Personal;
import boco.models.profile.Professional;
import boco.models.profile.Profile;
import boco.models.rental.CategoryType;
import boco.repository.profile.ProfileRepository;
import boco.repository.rental.CategoryTypeRepository;
import boco.service.rental.CategoryTypeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
class ProfileDetailsServiceTest {

    @InjectMocks
    private ProfileDetailsService profileDetailsService;

    @Mock
    private ProfileRepository profileRepository;

    @BeforeEach
    public void setUp(){
        Profile profile1 = new Profile();
        profile1.setId(1L);
        profile1.setUsername("profile");
        profile1.setPasswordHash("password1");
        profile1.setEmail("1@test.no");

        Profile profile2 = new Personal();
        profile2.setId(2L);
        profile2.setUsername("personal");
        profile2.setPasswordHash("password2");
        profile2.setEmail("2@test.no");

        Profile profile3 = new Professional();
        profile3.setId(3L);
        profile3.setUsername("professional");
        profile3.setPasswordHash("password3");
        profile3.setEmail("3@test.no");


        List<Profile> Profiles = new ArrayList<>(Arrays.asList(profile1, profile2, profile3));
        lenient().when(profileRepository.findAll()).thenReturn(Profiles);
        lenient().when(profileRepository.findProfileByUsername("profile")).thenReturn(Optional.of(profile1));
        lenient().when(profileRepository.findProfileByUsername("personal")).thenReturn(Optional.of(profile2));
        lenient().when(profileRepository.findProfileByUsername("professional")).thenReturn(Optional.of(profile3));
        lenient().when(profileRepository.findProfileByEmail("1@test.no")).thenReturn(Optional.of(profile1));
        lenient().when(profileRepository.findProfileByEmail("2@test.no")).thenReturn(Optional.of(profile2));
        lenient().when(profileRepository.findProfileByEmail("3@test.no")).thenReturn(Optional.of(profile3));
        lenient().when(profileRepository.findProfileByEmail("unknown@test.no")).thenReturn(Optional.empty());
        lenient().when(profileRepository.findProfileByEmail("unknown")).thenReturn(Optional.empty());
    }


    @Test
    void loadUserByUsername() {
        UserDetails user1 = profileDetailsService.loadUserByUsername("profile");
        UserDetails user2 = profileDetailsService.loadUserByUsername("personal");
        UserDetails user3 = profileDetailsService.loadUserByUsername("professional");

        assertEquals(user1.getUsername(), "profile");
        assertEquals(user1.getPassword(), "password1");

        assertEquals(user2.getUsername(), "personal");
        assertEquals(user2.getPassword(), "password2");

        assertEquals(user3.getUsername(), "professional");
        assertEquals(user3.getPassword(), "password3");
    }

    @Test
    void loadUserByEmail() {
        UserDetails user1 = profileDetailsService.loadUserByUsername("1@test.no");
        UserDetails user2 = profileDetailsService.loadUserByUsername("2@test.no");
        UserDetails user3 = profileDetailsService.loadUserByUsername("3@test.no");

        assertEquals(user1.getUsername(), "profile");
        assertEquals(user1.getPassword(), "password1");

        assertEquals(user2.getUsername(), "personal");
        assertEquals(user2.getPassword(), "password2");

        assertEquals(user3.getUsername(), "professional");
        assertEquals(user3.getPassword(), "password3");
    }


    @Test
    void errorWhenLoadWrongCredentials() {
        assertThrows(UsernameNotFoundException.class, ()->{
            profileDetailsService.loadUserByUsername("wrongUsername");
        });
        assertThrows(UsernameNotFoundException.class, ()->{
            profileDetailsService.loadUserByUsername("wrongEmail");
        });
        assertThrows(UsernameNotFoundException.class, ()->{
            profileDetailsService.loadUserByUsername("wrong@email.no");
        });

    }
}