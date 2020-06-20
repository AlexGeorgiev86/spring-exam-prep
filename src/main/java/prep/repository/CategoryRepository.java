package prep.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import prep.model.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, String> {
}
