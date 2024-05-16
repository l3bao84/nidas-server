package com.LeBao.sales.controllers;

import com.LeBao.sales.requests.ReviewRequest;
import com.LeBao.sales.services.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("/{productId}")
    public ResponseEntity<?> getReviewsByProductId(@PathVariable long productId,
                                                   @RequestParam(defaultValue = "0", required = false) int page) {
        return ResponseEntity.ok().body(reviewService.getReviewsByProductId(productId, page));
    }

    @PostMapping()
    public ResponseEntity<?> submitReview(ReviewRequest request, MultipartFile[] images) throws IOException {
        return ResponseEntity.ok().body(reviewService.createReview(request,images));
    }
}
