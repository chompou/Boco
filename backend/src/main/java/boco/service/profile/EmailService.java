package boco.service.profile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.net.MalformedURLException;
import java.net.URL;

@Component
public class EmailService extends SimpleMailMessage {

    @Autowired
    private JavaMailSender emailSender;


    public void sendVerificationMessage(String to, String url) throws MalformedURLException {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("idatt2106.4@gmail.com");
        message.setTo(to);
        message.setSubject("Verify Your Account");
        URL myUrl = new URL(url);
        message.setText("To verify your account, click here:\n" + myUrl + "\n");
        emailSender.send(message);
    }

    public void sendResetPasswordMessage(String to, String code) throws MalformedURLException {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("idatt2106.4@gmail.com");
        message.setTo(to);
        message.setSubject("Reset Your Password");
        message.setText("To reset your password, use the code below:\n " +
                code +"\nIf you don't want to reset your password, you can ignore this message");
        emailSender.send(message);
    }

    public void sendCreatedAccountMessage(String to){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("idatt2106.4@gmail.com");
        message.setTo(to);
        message.setSubject("New Account at Boco");
        message.setText("Thank you for opening an account with us \n\nBest regards Boco");
        emailSender.send(message);
    }
    public void sendContactFormFromUser(String from, String name, String issue){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo("idatt2106.4@gmail.com");
        message.setSubject("Issue from " + name);
        message.setText(issue);
        emailSender.send(message);
    }
}
