package boco.service.profile;

import boco.component.BocoHasher;
import boco.model.http.UpdatePasswordRequest;
import boco.model.profile.Personal;
import boco.model.profile.Professional;
import boco.model.profile.Profile;
import boco.repository.profile.PersonalRepository;
import boco.repository.profile.ProfessionalRepository;
import boco.repository.profile.ProfileRepository;
import boco.repository.rental.LeaseRepository;
import boco.service.security.JwtUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
class ProfileServiceTest {

    @InjectMocks
    private ProfileService profileService;

    @Mock
    private ProfileRepository profileRepository;
    @Mock
    private PersonalRepository personalRepository;
    @Mock
    private ProfessionalRepository professionalRepository;
    @Mock
    private LeaseRepository leaseRepository;

    @Mock
    private JwtUtil jwtUtil;



    @BeforeEach
    public void setup() {
        Personal p1 = new Personal("messi", "leo@psg.fr", "ligue 1", "LEO", "x", "Argentina", "12345678");
        p1.setId(1L);
        Professional p2 = new Professional("ronaldo", "cr7@manu.uk", "premier league", "CR7", "x", "Portugal", "12345678");
        p2.setId(2L);

        Personal  p3 = new Personal("kaka", "kaka@br.br", "x", "KAKA", "retired", "Brazil", "84267483");
        p3.setId(3L);
        Personal p4 = new Personal("ramos", "ramos@psg.fr", "x", "SERGIO", "spain", "Spain", "78592384");
        p4.setId(4L);


        lenient().when(personalRepository.save(p1)).thenReturn(p1);
        lenient().when(professionalRepository.save(p2)).thenReturn(p2);

        lenient().when(profileRepository.save(p1)).thenReturn(p1);
        lenient().when(profileRepository.save(p2)).thenReturn(p2);


        lenient().when(profileRepository.findById(1L)).thenReturn(Optional.of(p1));
        lenient().when(profileRepository.findById(2L)).thenReturn(Optional.of(p2));
        lenient().when(profileRepository.findById(3L)).thenReturn(Optional.of(p3));
        lenient().when(profileRepository.findById(4L)).thenReturn(Optional.of(p4));

        lenient().when(profileRepository.findProfileById(1L)).thenReturn(Optional.of(p1));
        lenient().when(profileRepository.findProfileById(2L)).thenReturn(Optional.of(p2));

        lenient().when(profileRepository.findProfileByEmail("leo@psg.fr")).thenReturn(Optional.of(p1));
        lenient().when(profileRepository.findProfileByEmail("cr7@manu.uk")).thenReturn(Optional.of(p2));
        lenient().when(profileRepository.findProfileByEmail("kaka@br.br")).thenReturn(Optional.of(p3));
        lenient().when(profileRepository.findProfileByEmail("ramos@psg.fr")).thenReturn(Optional.of(p4));

        lenient().when(profileRepository.findProfileByUsername("messi")).thenReturn(Optional.of(p1));
        lenient().when(profileRepository.findProfileByUsername("ronaldo")).thenReturn(Optional.of(p2));
        lenient().when(profileRepository.findProfileByUsername("kaka")).thenReturn(Optional.of(p3));
        lenient().when(profileRepository.findProfileByUsername("ramos")).thenReturn(Optional.of(p4));

        lenient().when(jwtUtil.extractUsername("messi")).thenReturn("messi");
        lenient().when(jwtUtil.extractUsername("ronaldo")).thenReturn("ronaldo");
        lenient().when(jwtUtil.extractUsername("kaka")).thenReturn("kaka");
        lenient().when(jwtUtil.extractUsername("ramos")).thenReturn("ramos");

        lenient().when(profileRepository.getIfContact(1L, 2L)).thenReturn(Optional.empty());
        lenient().when(profileRepository.getIfContact(2L, 1L)).thenReturn(Optional.empty());

        lenient().when(profileRepository.getIfContact(3L, 4L)).thenReturn(Optional.of(p4));
        lenient().when(profileRepository.getIfContact(4L, 3L)).thenReturn(Optional.of(p3));
    }

    @Test
    public void testVerifyProfile(){
        var res = profileService.verifyProfile(2L);
        Assertions.assertEquals(true, res.getBody().getIsVerified());
    }

    @Test
    public void testCheckIfProfileEmailExists(){
        var res = profileService.checkIfProfileEmailExists("leo@psg.fr");
        Assertions.assertNotEquals(null, res);
        Assertions.assertEquals("leo@psg.fr", res.getBody().getEmail());

        Assertions.assertEquals(null, profileService.checkIfProfileEmailExists("emil@mail.fr"));

    }
    @Test
    public void testCheckIfProfileUsernameExists(){
        var res = profileService.checkIfProfileUsernameExists("messi");
        Assertions.assertNotEquals(null, res);
        Assertions.assertEquals("messi", res.getBody().getUsername());

        Assertions.assertEquals(null, profileService.checkIfProfileUsernameExists("emil"));
    }

    @Test
    public void testChangePassword(){
        UpdatePasswordRequest goodRequest = new UpdatePasswordRequest("letmepass", "letmepass");
        var res = profileService.changePassword(goodRequest, "leo@psg.fr");
        Assertions.assertEquals(BocoHasher.encode("letmepass"), res.getBody().getPasswordHash());


        UpdatePasswordRequest badRequest = new UpdatePasswordRequest("letmepass", "dontletmepass");
        var res1 = profileService.changePassword(badRequest, "leo@psg.fr");
        Assertions.assertEquals(new ResponseEntity<Profile>(HttpStatus.NOT_ACCEPTABLE), res1);

        UpdatePasswordRequest forbiddenRequest = new UpdatePasswordRequest("letmepass", "letmepass");
        var res2 = profileService.changePassword(forbiddenRequest, "emil@mail.fr");
        Assertions.assertEquals(new ResponseEntity<Profile>(HttpStatus.FORBIDDEN), res2);
    }

    /**
     * Tests if getPublicProfile hides data when two profiles is not contacts.
     * In that case tlf and email should be null
     */
    @Test
    public void getPublicProfileHidesDataNotContacts() {
        var res1 = profileService.getPublicProfile(1L, "Bearer ronaldo").getBody();
        var res2 = profileService.getPublicProfile(2L, "Bearer messi").getBody();

        Assertions.assertNull(res1.getTlf());
        Assertions.assertNull(res1.getEmail());
        Assertions.assertNull(res2.getTlf());
        Assertions.assertNull(res2.getEmail());

    }

    @Test
    public void getPublicProfileDoesNotHideDataContacts() {
        var res1 = profileService.getPublicProfile(3L, "Bearer ramos").getBody();
        var res2 = profileService.getPublicProfile(4L, "Bearer kaka").getBody();

        Assertions.assertEquals("84267483", res1.getTlf());
        Assertions.assertEquals("kaka@br.br", res1.getEmail());
        Assertions.assertEquals("78592384", res2.getTlf());
        Assertions.assertEquals("ramos@psg.fr", res2.getEmail());
    }


    /**
     * Note that this test does not test the fields email and tlf, this is covered in the tests
     * getPublicProfileHidesDataNotContacts and getPublicProfileDoesNotHideDataContacts
     */
    @Test
    public void getPublicProfileReturnsCorrectData() {
        var res1 = profileService.getPublicProfile(1L, "Bearer ronaldo").getBody();
        var res2 = profileService.getPublicProfile(2L, "Bearer messi").getBody();

        Assertions.assertEquals("ligue 1", res1.getDescription());
        Assertions.assertEquals("LEO", res1.getDisplayName());
        Assertions.assertEquals(false, res1.getIsVerified());
        Assertions.assertEquals(null, res1.getDeactivated());

        Assertions.assertEquals("premier league", res2.getDescription());
        Assertions.assertEquals("CR7", res2.getDisplayName());
        Assertions.assertEquals(false, res2.getIsVerified());
        Assertions.assertEquals(null, res2.getDeactivated());

    }

    @Test
    public void getPrivateProfileReturnsCorrectData() {
        var res1 = profileService.getPrivateProfile("Bearer messi").getBody();
        var res2 = profileService.getPrivateProfile("Bearer ronaldo").getBody();

        Assertions.assertEquals(1L, res1.getId());
        Assertions.assertEquals("messi", res1.getUsername());
        Assertions.assertEquals("leo@psg.fr", res1.getEmail());
        Assertions.assertEquals("ligue 1", res1.getDescription());
        Assertions.assertEquals("LEO", res1.getDisplayName());
        Assertions.assertEquals("Argentina", res1.getAddress());
        Assertions.assertEquals(false, res1.getIsVerified());
        Assertions.assertEquals("12345678", res1.getTlf());
        Assertions.assertEquals(null, res1.getDeactivated());

        Assertions.assertEquals(2L, res2.getId());
        Assertions.assertEquals("ronaldo", res2.getUsername());
        Assertions.assertEquals("cr7@manu.uk", res2.getEmail());
        Assertions.assertEquals("premier league", res2.getDescription());
        Assertions.assertEquals("CR7", res2.getDisplayName());
        Assertions.assertEquals("Portugal", res2.getAddress());
        Assertions.assertEquals(false, res2.getIsVerified());
        Assertions.assertEquals("12345678", res2.getTlf());
        Assertions.assertEquals(null, res2.getDeactivated());
    }
}