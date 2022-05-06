package boco.repository.profile;

import boco.model.profile.Professional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for Professional
 */
@Repository
public interface ProfessionalRepository extends JpaRepository<Professional, Long> {
}
