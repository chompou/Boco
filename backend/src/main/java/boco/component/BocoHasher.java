package boco.component;

import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Component
public class BocoHasher {

    public static String encode(String unhashed) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        messageDigest.update(unhashed.getBytes());
        return new String(messageDigest.digest(), StandardCharsets.UTF_8);
    }

    public static String encode(Long unhashed) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        messageDigest.update((unhashed+"").getBytes());
        return new String(messageDigest.digest(), StandardCharsets.UTF_8);

    }
}
