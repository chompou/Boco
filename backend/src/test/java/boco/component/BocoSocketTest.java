package boco.component;



import org.junit.jupiter.api.*;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BocoSocketTest {

    @LocalServerPort
    private Integer port;

    @BeforeEach
    public void setup() {

    }

    public void setUpStreams() {

    }

    @AfterEach
    public void restoreStreams() {

    }

    @Test
    void onOpen(){

    }

    @Test
    void closeSession() {
    }

    @Test
    void onMessage() {
    }

    @Test
    void sendAllMessage() {
    }

    @Test
    void sendOneMessage() {
    }
}