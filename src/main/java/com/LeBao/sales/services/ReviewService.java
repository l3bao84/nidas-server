package com.LeBao.sales.services;

import com.LeBao.sales.entities.Review;
import com.LeBao.sales.repositories.ProductRepository;
import com.LeBao.sales.repositories.ReviewRepository;
import com.LeBao.sales.requests.ReviewRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;


@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;

    private final ProductRepository productRepository;

    private final UserService userService;

    private final StorageService storageService;

    public Page<Review> getReviewsByProductId(long id, int page) {
        int pageSize = 4;
        Pageable pageable;
        pageable = PageRequest.of(page, pageSize);
        return reviewRepository.getReviewsByProductId(id, pageable);
    }

    public Review createReview(ReviewRequest request, MultipartFile[] images) throws IOException {

        Review review = Review.builder()
                .content(request.getContent())
                .rating(request.getRate())
                .reviewDate(LocalDate.now())
                .imageReviews(images != null ? storageService.upload(images) : new ArrayList<>())
                .user(userService.getCurrentUsername())
                .product(productRepository.findById(request.getProductId()).orElse(null))
                .build();

        return reviewRepository.save(review);
    }
}
