package boco.repository.rental;

import boco.model.rental.CategoryType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository for CategoryType entity
 */
@Repository
public interface CategoryTypeRepository extends JpaRepository<CategoryType, Long> {

    /**
     * Finds category by matching name
     * @param name name of the category
     * @return the category found
     */
    Optional<CategoryType> findCategoryTypeByNameEquals(String name);
}
