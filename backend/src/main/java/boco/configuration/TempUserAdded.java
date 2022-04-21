package boco.configuration;

import boco.models.profile.Profile;
import boco.repository.profile.ProfileRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TempUserAdded {

    @Bean
    CommandLineRunner simpleRunner(ProfileRepository profileRepository){
        return args ->{
            Profile profile = new Profile();
            profile.setUsername("hello");
            profile.setPasswordHash("goodbye");

            profileRepository.save(profile);
        };
    }

}
