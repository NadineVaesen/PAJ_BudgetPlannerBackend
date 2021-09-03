package be.pxl.paj.budgetplanner.service;

import be.pxl.paj.budgetplanner.dao.CategoryRepository;
import be.pxl.paj.budgetplanner.dao.PaymentRepository;
import be.pxl.paj.budgetplanner.dto.CategoryCreateResource;
import be.pxl.paj.budgetplanner.dto.CategoryDTO;
import be.pxl.paj.budgetplanner.entity.Category;
import be.pxl.paj.budgetplanner.entity.Payment;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final PaymentRepository paymentRepository;

    public CategoryService(CategoryRepository categoryRepository, PaymentRepository paymentRepository) {
        this.categoryRepository = categoryRepository;
        this.paymentRepository = paymentRepository;
    }

    public List<CategoryDTO> getAllCategories() {
        return categoryRepository.findAll().stream().map(this::mapCategoryToDTO).collect(Collectors.toList());
    }

    private CategoryDTO mapCategoryToDTO(Category category) {
        return new CategoryDTO(category);

    }

    @PostMapping
    public ResponseEntity<String> createCategory(CategoryCreateResource categoryCreateResource) {
        Category category = mapResourceToCategory(categoryCreateResource);
        if(categoryRepository.findCategoryByName(category.getName()).getName().equals(categoryCreateResource.getName())){
            return ResponseEntity.badRequest().body("There already exists a label with name [ "+categoryCreateResource.getName()+"]");
        } else {
            categoryRepository.save(category);
            return ResponseEntity.ok().body("Category successfully added");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long id) {
        var categorySearch = categoryRepository.findById(id);


        if (categorySearch.isPresent()) {
            Category category = categorySearch.get();
            List<Payment> allPayments = paymentRepository.findAllByCategory(category);
            if(allPayments.isEmpty()) {
                categoryRepository.delete(category);
                return ResponseEntity.ok().body("Category is deleted.");
            } else {
                return ResponseEntity.badRequest().body("Label[" + category.getName() + "] is in use. Remove the payments firt or change their label.");
            }
        }
        return ResponseEntity.badRequest().body("Something went wrong with the label.");
    }

    private Category mapResourceToCategory(CategoryCreateResource categoryCreateResource) {
        Category category = new Category();
        category.setName(categoryCreateResource.getName());
        return category;
    }
}
