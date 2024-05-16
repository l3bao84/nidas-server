package com.LeBao.sales.repositories;

import com.LeBao.sales.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT p FROM Product p WHERE p.category.categoryId = :categoryId")
    List<Product> findAllByCategoryId(Long categoryId);

    Page<Product> findByProductNameContainingIgnoreCase(String keyword, Pageable pageable);

    @Query("select p from Product p")
    Page<Product> getAll(Pageable pageable);

    @Query("select p from Product p where p.productName like %:name%")
    Page<Product> search(Pageable pageable, @Param("name") String name);
}
