package prep.service;


import prep.model.entity.Category;
import prep.model.entity.CategoryName;

public interface CategoryService {
    void initCategories();
    Category findByName(CategoryName categoryName);
}
