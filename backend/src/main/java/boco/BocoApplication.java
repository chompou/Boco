package boco;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BocoApplication {

    public static void main(String[] args) {
        SpringApplication.run(BocoApplication.class, args);
    }

}
