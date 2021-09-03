package be.pxl.paj.budgetplanner.rest;

import be.pxl.paj.budgetplanner.dto.CategoryCreateResource;
import be.pxl.paj.budgetplanner.dto.CategoryDTO;
import be.pxl.paj.budgetplanner.entity.Category;
import be.pxl.paj.budgetplanner.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public List<CategoryDTO> getAllLabels() {
        return categoryService.getAllCategories();
    }

    @PostMapping
    public ResponseEntity<String> createCategory(CategoryCreateResource categoryCreateResource){
        return categoryService.createCategory(categoryCreateResource);
    }
}
