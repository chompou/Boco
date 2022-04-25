package boco.controller.models;

import boco.models.http.ProfileRequest;
import boco.models.profile.Profile;
import boco.repository.profile.ProfileRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProfileControllerTest {
    @Autowired
    private ProfileController profileController;

    @Autowired
    private ProfileRepository profileRepository;

    @Test
    @Transactional
    public void createUserSavesUserInDatabase() {

        ProfileRequest req1 = new ProfileRequest("brad", "brad.gibson@example.com", "x", "GIBSON", "x", "9278 new road", "99999999", true);
        ProfileRequest req2 = new ProfileRequest("ron", "ron@mail.com", "x", "ron", "x", "9278 new road", "88888888", false);
        ProfileRequest req3 = new ProfileRequest("leo", "leo@psg.fr", "x", "LEO10", "x", "barcelona", "77777777", true);

        profileController.createProfile(req1);
        profileController.createProfile(req2);
        profileController.createProfile(req3);

        Profile p1 = profileRepository.findProfileByUsername("brad").get();
        Profile p2 = profileRepository.findProfileByUsername("ron").get();
        Profile p3 = profileRepository.findProfileByUsername("leo").get();
        profileRepository.flush();

        assertNotNull(p1);
        assertNotNull(p2);
        assertNotNull(p3);
    }

    @Test
    @Transactional
    public void getProfileGetsProfileFromDatabase() {
        Profile p1 = new Profile("brad", "brad.gibson@example.com", "x", "GIBSON", "x", "9278 new road", "99999999");
        Profile p2 = new Profile("ron", "ron@mail.com", "x", "ron", "x", "9278 new road", "88888888");
        Profile p3 = new Profile("leo", "leo@psg.fr", "x", "LEO10", "x", "barcelona", "77777777");

        profileRepository.save(p1);
        profileRepository.save(p2);
        profileRepository.save(p3);
        Profile pr1 = profileRepository.getById(p1.getId());
        Profile pr2 = profileRepository.getById(p2.getId());
        Profile pr3 = profileRepository.getById(p3.getId());
        profileRepository.flush();

        assertNotNull(pr1);
        assertNotNull(pr2);
        assertNotNull(pr3);
    }
}