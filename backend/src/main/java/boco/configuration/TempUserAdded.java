package boco.configuration;

import boco.models.profile.Profile;
import boco.repository.profile.ProfileRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Temporary fill for database
 */
@Configuration
public class TempUserAdded {

    /**
     * Simple runner command line runner.
     *
     * @param profileRepository the profile repository
     * @return the command line runner
     */
    CommandLineRunner simpleRunner(ProfileRepository profileRepository){
        return args ->{
            Profile profile = new Profile();

            profileRepository.save(profile);
        };
    }

}
