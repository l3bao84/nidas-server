package com.LeBao.sales.controllers;

import com.LeBao.sales.entities.Category;
import com.LeBao.sales.services.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<Category>> loadAllCats() {
        return ResponseEntity.ok().body(categoryService.loadAllCategories());
    }

    //@PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/quickLinks")
    public ResponseEntity<List<Category>> getQuickLinks() {
        return ResponseEntity.ok().body(categoryService.getQuickLinks());
    }

    @PostMapping("/addCategory")
    public ResponseEntity<String> addCategory(@RequestBody Category category) {
        categoryService.addCategory(category);
        return ResponseEntity.ok("Add a category successfully");
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<?> getCategoryById(@PathVariable Long categoryId) {
        if(categoryService.getCategoryById(categoryId) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No category has id: " + categoryId);
        }else {
            return ResponseEntity.ok().body(categoryService.getCategoryById(categoryId));
        }
    }

    @PostMapping("/updateCategory/{categoryId}")
    public ResponseEntity<String> updateCategory(@PathVariable Long categoryId, @RequestBody Category category) {
        categoryService.updateCategory(categoryId,category);
        return ResponseEntity.ok("Update this category successfully");
    }

    @PostMapping("/deleteCategory/{categoryId}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long categoryId) {
        categoryService.deleteCategory(categoryId);
        return ResponseEntity.ok("Delete this category successfully");
    }
}
