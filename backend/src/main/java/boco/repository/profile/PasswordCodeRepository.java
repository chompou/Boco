package boco.repository.profile;

import boco.model.profile.PasswordCode;
import boco.model.profile.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PasswordCodeRepository extends JpaRepository<PasswordCode, Long> {
    Optional<PasswordCode> findPasswordCodeByProfile(Profile profile);
}
