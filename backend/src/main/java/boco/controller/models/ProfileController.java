package boco.controller.models;

import boco.models.http.ProfileRequest;
import boco.models.profile.Profile;
import boco.service.profile.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profile")
public class ProfileController {
    private final ProfileService profileService;

    @Autowired
    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping("/{profile_id}")
    public ResponseEntity<Profile> getProfile(@PathVariable(value = "profile_id") Long profileId) {
        return profileService.getProfile(profileId, (long) 1);
    }

    @PostMapping("/")
    public ResponseEntity<Profile> createProfile(@RequestBody ProfileRequest profileRequest) {
        return profileService.createProfile(profileRequest);
    }
}
