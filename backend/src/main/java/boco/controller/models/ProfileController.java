package boco.controller.models;

import boco.models.http.ProfileRequest;
import boco.models.http.PrivateProfileResponse;
import boco.models.http.PublicProfileResponse;
import boco.service.profile.EmailService;
import boco.service.profile.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profile")
public class ProfileController {
    private final ProfileService profileService;
    private final EmailService emailService;

    @Autowired
    public ProfileController(ProfileService profileService, EmailService emailService) {
        this.profileService = profileService;
        this.emailService = emailService;
    }

    @GetMapping("/{profile_id}")
    public ResponseEntity<PublicProfileResponse> getProfile(@PathVariable(value = "profile_id") Long profileId,
            @RequestHeader(name="Authorization", required = false) String token) {
        return profileService.getPublicProfile(profileId, token);
    }

    @PostMapping("")
    public ResponseEntity<PrivateProfileResponse> createProfile(@RequestBody ProfileRequest profileRequest) {
        ResponseEntity<PrivateProfileResponse> responseEntity = profileService.createProfile(profileRequest);
        /**
         * Code for sending welcome message to new users, inactive under development.
        if (responseEntity.getBody() != null){
            emailService.sendCreatedAccountMessage(profileRequest.getEmail());
        }
         
         */
        return responseEntity;
    }


}
