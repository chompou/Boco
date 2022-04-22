package boco.repository.profile;

import boco.models.profile.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProfileRepository extends JpaRepository <Profile, Long> {
    Optional<Profile> findProfileByUsernameOrEmail(String username, String email);
    Optional<Profile> findProfileByUsername(String username);
    Optional<Profile> findProfileByEmail(String email);
}