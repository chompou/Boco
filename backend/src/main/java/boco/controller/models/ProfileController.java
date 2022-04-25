package boco.controller.models;

import boco.models.http.ProfileRequest;
import boco.models.profile.Profile;
import boco.service.profile.ProfileService;
import boco.service.security.VerificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.MalformedURLException;

@RestController
@RequestMapping("/api/profile")
public class ProfileController {
    private final ProfileService profileService;
    private  final VerificationService verificationService;

    @Autowired
    public ProfileController(ProfileService profileService, VerificationService verificationService) {
        this.profileService = profileService;
        this.verificationService = verificationService;
    }

    @GetMapping("/{profile_id}")
    public ResponseEntity<Profile> getProfile(@PathVariable(value = "profile_id") Long profileId) {
        return profileService.getProfile(profileId);
    }

    @PostMapping()
    public ResponseEntity<Profile> createProfile(@RequestBody ProfileRequest profileRequest) {
        return profileService.createProfile(profileRequest);
    }

    @GetMapping("/verification/getVerified/{profile_id}")
    public String verifyProfile(@PathVariable(value = "profile_id") Long profileId){
        profileService.verifyProfile(profileId);
        return "You are now verified";
    }
    @GetMapping("/verification/{profile_id}")
    public void sendVerificationMail(@PathVariable(value = "profile_id") Long profileId) throws MalformedURLException {
        String url = "http://localhost:8080/api/profile/verification/getVerified/"+ profileId;
        verificationService.sendVerificationMessage(getProfile(profileId).getBody().getEmail(), url);
    }
}
