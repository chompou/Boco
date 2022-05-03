package boco.controller.models;

import boco.models.profile.Profile;
import boco.service.profile.ProfileService;
import boco.service.profile.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.MalformedURLException;
@RestController
@RequestMapping("/api/my")
public class VerificationController {
    private final ProfileService profileService;
    private final EmailService emailService;

    @Autowired
    public VerificationController(ProfileService profileService, EmailService emailService) {
        this.profileService = profileService;
        this.emailService = emailService;
    }
    @GetMapping("/verification/getVerified/{profile_id}")
    public ResponseEntity<Profile> verifyProfile(@PathVariable(value = "profile_id") Long profileId){
        return profileService.verifyProfile(profileId);
    }

    @GetMapping("/verification/{profile_id}")
    public void sendVerificationMail(@PathVariable(value = "profile_id") Long profileId) {
        String url = "http://localhost:8080/api/my/verification/getVerified/"+ profileId;
        //TODO secure this endpoint better, (ask Elias)
        /**
        try {
            emailService.sendVerificationMessage(profileService.getEmail(profileId).getBody(), url);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
         */
    }
}
