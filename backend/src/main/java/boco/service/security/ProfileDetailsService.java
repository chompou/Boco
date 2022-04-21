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

@Service
public class ProfileDetailsService implements UserDetailsService {
    @Autowired
    private ProfileRepository profileRepository;

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        String username = "";
        String email = "";
        if (usernameOrEmail.contains("@")){
            email = usernameOrEmail;
        }else{
            username = usernameOrEmail;
        }
        Optional<Profile> user = profileRepository.findProfileByUsernameOrEmail(username, email);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("Could not find user");
        }
        return new ProfileDetails(user.get());
    }
}
