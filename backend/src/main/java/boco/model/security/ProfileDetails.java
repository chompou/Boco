package boco.model.security;

import boco.model.profile.Profile;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;

/**
 * The type Profile implemented from UserDetails from spring security
 */
public class ProfileDetails implements UserDetails {
    private final Profile profile;

    /**
     * Default constructor with all params
     *
     * @param profile the profile
     */
    public ProfileDetails(Profile profile) {
        this.profile = profile;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_ADMIN");
        return Arrays.asList(authority);
    }

    @Override
    public String getPassword() {
        return profile.getPasswordHash();
    }

    @Override
    public String getUsername() {
        return profile.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        if (profile.getDeactivated() == null){
            return true;
        }
        return profile.getDeactivated().toLocalDateTime().isAfter(LocalDateTime.now());
    }
}




























