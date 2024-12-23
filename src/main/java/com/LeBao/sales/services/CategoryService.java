package com.LeBao.sales.services;

import com.LeBao.sales.entities.Category;
import com.LeBao.sales.entities.Product;
import com.LeBao.sales.repositories.CategoryRepository;
import com.LeBao.sales.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    public List<Category> loadAllCategories() {
        return categoryRepository.findAll();
    }

    public Category getCategoryById(Long categoryId) {
        if(categoryRepository.findById(categoryId).isPresent()) {
            return categoryRepository.findById(categoryId).get();
        }else {
            return null;
        }
    }

    public void addCategory(Category category) {
        categoryRepository.save(category);
    }

    public void updateCategory(Long categoryId, Category category) {
        Category foundCategory = categoryRepository.findById(categoryId).get();
        foundCategory.setCategoryName(category.getCategoryName());
        categoryRepository.save(foundCategory);
    }

    public List<Category> getQuickLinks() {
        List<Category> allCats = categoryRepository.findAll();
        allCats.remove(0);
        List<Category> randomCat = new ArrayList<>();
        Random random = new Random();
        for(int i = 0; i <= 6; i++) {
            int randomPosition = random.nextInt(allCats.size());
            randomCat.add(allCats.get(randomPosition));
            allCats.remove(randomPosition);
        }
        return randomCat;
    }

    public void deleteCategory(Long categoryId) {
        List<Product> productList = productRepository.findAll();
        for (Product product:productList) {
            if(product.getCategory().getCategoryId() == categoryId) {
                product.setCategory(categoryRepository.findById(1L).get());
                categoryRepository.findById(1L).get().getProducts().add(product);
                productRepository.save(product);
                categoryRepository.save(categoryRepository.findById(1L).get());
            }
        }
        categoryRepository.deleteById(categoryId);
    }
}
