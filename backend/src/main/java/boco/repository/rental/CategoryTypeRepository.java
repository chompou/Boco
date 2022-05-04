package boco.repository.rental;

import boco.model.rental.CategoryType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryTypeRepository extends JpaRepository<CategoryType, Long> {

    Optional<CategoryType> findCategoryTypeByNameEquals(String name);
}
