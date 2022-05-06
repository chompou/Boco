package boco.repository.profile;

import boco.model.profile.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

/**
 * Repository for Profile
 */
@Repository
public interface ProfileRepository extends JpaRepository <Profile, Long> {
    /**
     * @param username Username of profile to find
     * @return Optional of profile of username
     */
    Optional<Profile> findProfileByUsername(String username);

    /**
     * @param email Email of profile to find
     * @return Optional of profile of username
     */
    Optional<Profile> findProfileByEmail(String email);

    /**
     * Returns true if two profile is contacts, else false. Two profiles is contacts if
     * profile A has a lease with profile B.
     *
     * @param id1 Profile ID of profile A
     * @param id2 Profile ID of profile B
     * @return Body null if users are not contacts, else a profile is contained in the optional
     */
    @Query("SELECT p FROM Profile p WHERE p.id = ?2 AND p IN" +
            "(SELECT p1 FROM Profile p1 WHERE EXISTS " +
            "(SELECT li FROM Listing li WHERE li.profile.id = p1.id AND EXISTS" +
            "(SELECT le FROM Lease le WHERE le.listing.id = li.id AND le.profile.id = ?1)))" +
            "OR p.id = ?2  AND p IN " +
            "(SELECT p1 FROM Profile p1 WHERE EXISTS " +
            "(SELECT li FROM Listing li WHERE li.profile.id = ?1 AND EXISTS" +
            "(SELECT le FROM Lease le WHERE le.listing.id = li.id AND le.profile.id = p1.id)))")
    Optional<Profile> getIfContact(long id1, long id2);

    /**
     * @param id ID of profile to find
     * @return Optional of profile
     */
    Optional<Profile> findProfileById(Long id);

    /**
     * Gets all profiles deactivated before a given time
     * @param time The time
     * @return List of profiles
     */
    List<Profile> getAllByDeactivatedBefore(Timestamp time);
}