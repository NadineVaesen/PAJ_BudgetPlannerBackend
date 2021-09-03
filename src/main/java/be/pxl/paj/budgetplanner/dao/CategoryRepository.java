package be.pxl.paj.budgetplanner.dao;


import be.pxl.paj.budgetplanner.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

	// REMOVE IF NECESSARY
	//void save(Category category);
    Category findCategoryByName(String name);
}
