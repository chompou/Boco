package boco.service.profile;

import boco.model.http.profile.UpdatePasswordRequest;
import boco.model.profile.PasswordCode;
import boco.model.rental.Lease;
import boco.repository.profile.PasswordCodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Random;

@Service
public class PasswordCodeService {
    @Autowired
    private PasswordCodeRepository passwordCodeRepository;

    public String generateCode() {
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();
        StringBuilder buffer = new StringBuilder(targetStringLength);
        for (int i = 0; i < targetStringLength; i++) {
            int randomLimitedInt = leftLimit + (int)
                    (random.nextFloat() * (rightLimit - leftLimit + 1));
            buffer.append((char) randomLimitedInt);
        }
        String generatedString = buffer.toString();
        return generatedString;
    }
    public void removeDangling() {
        Date aHourAgo = new Date(new Date().getTime() - (1000*60*60));
        List<PasswordCode> passwordCodes = passwordCodeRepository.findAll();
        for (PasswordCode passwordCode:passwordCodes) {
            if (passwordCode.getTimestamp().before(aHourAgo)){
                try {
                    passwordCodeRepository.delete(passwordCode);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }
}
