package boco.controller.models;

import boco.models.profile.Profile;
import boco.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public Profile getProfile(@PathVariable(value = "profile_id") String profileId) {
        return profileService.
    }
}
