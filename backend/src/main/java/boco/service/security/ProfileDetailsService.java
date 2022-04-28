package boco.service.security;

import boco.models.profile.Profile;
import boco.models.security.ProfileDetails;
import boco.repository.profile.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * ProfileDetails service, finds a user by either email or username used for user authentication
 */
@Service
public class ProfileDetailsService implements UserDetailsService {
    @Autowired
    private ProfileRepository profileRepository;

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        Optional<Profile> user;
        if (usernameOrEmail.contains("@")){
            user = profileRepository.findProfileByEmail(usernameOrEmail);
        }else{
            user = profileRepository.findProfileByUsername(usernameOrEmail);
        }
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("Could not find user");
        }
        return new ProfileDetails(user.get());
    }
}
