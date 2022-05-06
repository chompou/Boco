package boco.controller.model;

import boco.model.http.profile.ProfileRequest;
import boco.model.http.profile.PrivateProfileResponse;
import boco.model.http.profile.PublicProfileResponse;
import boco.service.profile.EmailService;
import boco.service.profile.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * This class defines endpoints for profile operations
 */
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

    /**
     * Gets profile
     *
     * @param profileId ID of the profile
     * @param token Authorization header
     * @return
     */
    @GetMapping("/{profile_id}")
    public ResponseEntity<PublicProfileResponse> getProfile(@PathVariable(value = "profile_id") Long profileId,
            @RequestHeader(name="Authorization", required = false) String token) {
        return profileService.getPublicProfile(profileId, token);
    }

    /**
     * Creates a new profile and sends an email to greet the new profile.
     *
     * @param profileRequest Data of the new profile
     * @return The created profile
     */
    @PostMapping("")
    public ResponseEntity<PrivateProfileResponse> createProfile(@RequestBody ProfileRequest profileRequest) {
        ResponseEntity<PrivateProfileResponse> responseEntity = profileService.createProfile(profileRequest);
        if (responseEntity.getBody() != null){
            emailService.sendCreatedAccountMessage(profileRequest.getEmail());
        }
        return responseEntity;
    }

}
