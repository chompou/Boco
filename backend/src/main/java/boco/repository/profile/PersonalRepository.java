package boco.repository.profile;

import boco.models.profile.Personal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonalRepository extends JpaRepository<Personal, Long> {
}
