package prep.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import prep.model.entity.Category;
import prep.model.entity.CategoryName;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, String> {

    Optional<Category> findByCategoryName(CategoryName categoryName);
}
