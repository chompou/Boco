package boco.repository.profile;

import boco.model.profile.PasswordCode;
import boco.model.profile.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository for PasswordCode entity
 */
public interface PasswordCodeRepository extends JpaRepository<PasswordCode, Long> {
    /**
     * Finds the password recovery code for a profile
     *
     * @param profile Profile to get password recovery code for
     * @return Option of password code
     */
    Optional<PasswordCode> findPasswordCodeByProfile(Profile profile);
}
