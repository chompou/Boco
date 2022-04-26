package boco.controller.models;

import boco.models.http.ProfileRequest;
import boco.models.http.PrivateProfileResponse;
import boco.models.http.PublicProfileResponse;
import boco.service.profile.ProfileService;
import boco.service.security.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.MalformedURLException;

@RestController
@RequestMapping("/api/profile")
public class ProfileController {
    private final ProfileService profileService;

    @Autowired
    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping("/{profile_id}")
    public ResponseEntity<PublicProfileResponse> getProfile(@PathVariable(value = "profile_id") Long profileId) {
        return profileService.getProfile(profileId, (long) 1);
    }

    @PostMapping("")
    public ResponseEntity<PrivateProfileResponse> createProfile(@RequestBody ProfileRequest profileRequest) {
        return profileService.createProfile(profileRequest);
    }


}
