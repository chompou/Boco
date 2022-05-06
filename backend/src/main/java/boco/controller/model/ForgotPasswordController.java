package boco.controller.model;

import boco.model.http.profile.UpdatePasswordRequest;
import boco.model.profile.PasswordCode;
import boco.model.profile.Profile;
import boco.repository.profile.PasswordCodeRepository;
import boco.service.profile.PasswordCodeService;
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
    private final PasswordCodeService passwordCodeService;
    private final PasswordCodeRepository passwordCodeRepository;

    @Autowired
    public ForgotPasswordController(EmailService emailService, ProfileService profileService, PasswordCodeService passwordCodeService, PasswordCodeRepository passwordCodeRepository){
        this.emailService = emailService;
        this.profileService = profileService;
        this.passwordCodeService = passwordCodeService;
        this.passwordCodeRepository = passwordCodeRepository;

    }

    @GetMapping("/{email}")
    public ResponseEntity<HttpStatus> sendForgotPasswordMail(@PathVariable(value = "email") String email) throws MalformedURLException {
        if (profileService.checkIfProfileEmailExists(email) != null){
            String code = passwordCodeService.generateCode();
            PasswordCode passwordCode = new PasswordCode(profileService.checkIfProfileEmailExists(email).getBody(), code);
            passwordCodeRepository.save(passwordCode);
            emailService.sendResetPasswordMessage(email, code);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/change/{email}")
    public ResponseEntity<Profile> changePassword(@PathVariable(value = "email") String email, @RequestBody UpdatePasswordRequest updatePasswordRequest){
        return profileService.changePassword(updatePasswordRequest, email);
    }

}
