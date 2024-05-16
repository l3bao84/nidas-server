package com.LeBao.sales.repositories;

import com.LeBao.sales.entities.Product;
import com.LeBao.sales.entities.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query("SELECT r FROM Review r WHERE r.product.productId = :id")
    Page<Review> getReviewsByProductId(@Param("id") long id, Pageable pageable);
}
