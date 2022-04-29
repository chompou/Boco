package boco.controller.models;

import boco.models.http.UpdatePasswordRequest;
import boco.models.profile.Profile;
import boco.service.profile.ProfileService;
import boco.service.profile.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.MalformedURLException;

@RestController
@RequestMapping("/api/forgot-password")
public class ForgotPasswordController {
    private final EmailService emailService;
    private final ProfileService profileService;

    @Autowired
    public ForgotPasswordController(EmailService emailService, ProfileService profileService){
        this.emailService = emailService;
        this.profileService = profileService;

    }

    @GetMapping("/{email}")
    public ResponseEntity<HttpStatus> sendForgotPasswordMail(@PathVariable(value = "email") String email) throws MalformedURLException {
        /**
        if (profileService.checkIfProfileEmailExists(email) != null){
            String url = "http://localhost:8080/api/forgot-password/change/"+ email;
            emailService.sendResetPasswordMessage(email, url);
            return new ResponseEntity<>(HttpStatus.OK);
        }
         */
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/change/{email}")
    public ResponseEntity<Profile> changePassword(@PathVariable(value = "email") String email, @RequestBody UpdatePasswordRequest updatePasswordRequest){
        return profileService.changePassword(updatePasswordRequest, email);
    }

}
