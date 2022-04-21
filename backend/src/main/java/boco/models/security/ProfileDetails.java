package boco.models.security;

import boco.models.profile.Profile;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;

public class ProfileDetails implements UserDetails {
    private Profile profile;

    public ProfileDetails(Profile profile) {
        this.profile = profile;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("user");
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
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        if (profile.getDeactivated() == null){
            return false;
        }
        return profile.getDeactivated().toLocalDateTime().isBefore(LocalDateTime.now());
    }
}




























