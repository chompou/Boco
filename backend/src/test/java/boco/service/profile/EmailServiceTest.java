package boco.service.profile;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.util.ReflectionTestUtils;

import java.net.MalformedURLException;
import java.util.Objects;

import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class EmailServiceTest {
    @InjectMocks
    EmailService emailService;

    @Mock
    private JavaMailSender javaMailSender;

    ArgumentCaptor<SimpleMailMessage> valueCapture;


    @BeforeEach
    public void before(){
        emailService = new EmailService();
        javaMailSender = mock(JavaMailSender.class);
        ReflectionTestUtils.setField(emailService, "emailSender", javaMailSender);
        valueCapture = ArgumentCaptor.forClass(SimpleMailMessage.class);
        lenient().doNothing().when(javaMailSender).send(valueCapture.capture());
    }

    @Test
    void assertThorwsMalformedURLExceptions(){
        Assertions.assertThrows(MalformedURLException.class, () ->{
            emailService.sendVerificationMessage("test@test.no", "Not OK URL æøÅ");
        });
        Assertions.assertThrows(MalformedURLException.class, () ->{
            emailService.sendResetPasswordMessage("test@test.no", "Not OK URL æøÅ");
        });
    }

    @Test
    void sendVerificationMessage() throws MalformedURLException {
        Assertions.assertDoesNotThrow(() -> {
            emailService.sendVerificationMessage("test@test.no", "http://localhost:8080/testUrl");
        });
        int originalCaptureSize = valueCapture.getAllValues().size();
        emailService.sendVerificationMessage("test@test.no", "http://localhost:8080/testUrl");
        Assertions.assertTrue(valueCapture.getAllValues().size()>originalCaptureSize);
        Assertions.assertEquals(valueCapture.getValue().getFrom(), "idatt2106.4@gmail.com");
        Assertions.assertEquals(Objects.requireNonNull(valueCapture.getValue().getTo())[0], "test@test.no");
        Assertions.assertEquals(valueCapture.getValue().getSubject(), "Verify Your Account");
        Assertions.assertTrue(valueCapture.getValue().getText().contains("http://localhost:8080/testUrl"));
    }

    @Test
    void sendResetPasswordMessage() throws MalformedURLException {
        Assertions.assertDoesNotThrow(() -> {
            emailService.sendResetPasswordMessage("test@test.no", "http://localhost:8080/testUrl");
        });
        int originalCaptureSize = valueCapture.getAllValues().size();
        emailService.sendResetPasswordMessage("test@test.no", "http://localhost:8080/testUrl");
        Assertions.assertTrue(valueCapture.getAllValues().size()>originalCaptureSize);
        Assertions.assertEquals(valueCapture.getValue().getFrom(), "idatt2106.4@gmail.com");
        Assertions.assertEquals(Objects.requireNonNull(valueCapture.getValue().getTo())[0], "test@test.no");
        Assertions.assertEquals(valueCapture.getValue().getSubject(), "Reset Your Password");
        Assertions.assertTrue(valueCapture.getValue().getText().contains("http://localhost:8080/testUrl"));
    }

    @Test
    void sendCreatedAccountMessage() {
        int originalCaptureSize = valueCapture.getAllValues().size();
        emailService.sendCreatedAccountMessage("test@test.no");
        Assertions.assertTrue(valueCapture.getAllValues().size()>originalCaptureSize);
        Assertions.assertEquals(valueCapture.getValue().getFrom(), "idatt2106.4@gmail.com");
        Assertions.assertEquals(Objects.requireNonNull(valueCapture.getValue().getTo())[0], "test@test.no");
        Assertions.assertEquals(valueCapture.getValue().getSubject(), "New Account at Boco");
    }

    @Test
    void sendContactFormFromUser() {
        int originalCaptureSize = valueCapture.getAllValues().size();
        emailService.sendContactFormFromUser("test@test.no", "test", "issue");
        Assertions.assertTrue(valueCapture.getAllValues().size()>originalCaptureSize);
        Assertions.assertEquals(valueCapture.getValue().getFrom(), "test@test.no");
        Assertions.assertEquals(Objects.requireNonNull(valueCapture.getValue().getTo())[0], "idatt2106.4@gmail.com");
        Assertions.assertEquals(valueCapture.getValue().getSubject(), "Issue from test");
    }
}