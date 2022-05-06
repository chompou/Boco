package boco.repository.profile;

import boco.model.profile.Personal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for Personal
 */
@Repository
public interface PersonalRepository extends JpaRepository<Personal, Long> {
}
