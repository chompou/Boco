package boco.service.profile;

import boco.models.http.ProfileRequest;
import boco.models.profile.Personal;
import boco.models.profile.Professional;
import boco.models.rental.Lease;
import boco.models.rental.Listing;
import boco.models.rental.Review;
import boco.repository.profile.PersonalRepository;
import boco.repository.profile.ProfessionalRepository;
import boco.repository.profile.ProfileRepository;
import boco.repository.rental.LeaseRepository;
import boco.service.security.JwtUtil;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.same;
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
        Personal personal = new Personal("messi", "leo@psg.fr", "x", "LEO", "x", "Argentina", "12345678");
        Professional professional = new Professional("ronaldo", "cr7@manu.uk", "x", "CR7", "x", "Portugal", "12345678");
        professional.setId((long) 1);

        //lenient().when(personalRepository.save(same("messi"))).thenReturn(personal));
        lenient().when(professionalRepository.save(professional)).thenReturn(professional);
        lenient().when(profileRepository.save(professional)).thenReturn(professional);
        lenient().when(profileRepository.findProfileById((long) 1)).thenReturn(Optional.of(professional));
    }

    @Test
    public void createProfileTest() {
        var res = profileService.createProfile(null);
        System.out.println(res);
    }

    @Test
    public void TestVerifyProfile(){
        var res = profileService.verifyProfile((long) 1);
        Assertions.assertEquals(true, res.getBody().getIsVerified());
    }

}