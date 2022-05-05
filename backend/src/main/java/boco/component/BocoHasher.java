package boco.component;

import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

@Component
public class BocoHasher {

    public static String encode(String unhashed){
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(unhashed.getBytes());
            return new String(messageDigest.digest(), StandardCharsets.UTF_8);
        }catch (Exception ignored){}
        return unhashed;
    }

    public static String encode(Long unhashed){
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update((unhashed+"").getBytes());
            return new String(messageDigest.digest(), StandardCharsets.UTF_8);
        }catch (Exception ignored){}
        return unhashed.toString();
    }
}
