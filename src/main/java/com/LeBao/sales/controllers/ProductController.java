package com.LeBao.sales.controllers;

import com.LeBao.sales.entities.Product;
import com.LeBao.sales.requests.ProReq;
import com.LeBao.sales.requests.SearchProReq;
import com.LeBao.sales.responses.DataResponse;
import com.LeBao.sales.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/product")
@CrossOrigin
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping("/topPicks")
    public ResponseEntity<List<Product>> getTopPicks() {
        return ResponseEntity.ok().body(productService.getTopPickProducts());
    }

    @GetMapping("/recommendations")
    public ResponseEntity<List<Product>> getRecommendedProducts() {
        return ResponseEntity.ok().body(productService.getRecommendedProducts());
    }

    @GetMapping("/{productId}")
    public ResponseEntity<?> getProduct(@PathVariable Long productId) {
        if(productService.getProduct(productId) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No product has id: " + productId);
        }else {
            return ResponseEntity.ok().body(productService.getProduct(productId));
        }
    }

    @GetMapping("/admin")
    public ResponseEntity<?> getAll(SearchProReq req) {
        return ResponseEntity.ok().body(productService.getAll(req));
    }

    @PostMapping("/admin/add")
    public ResponseEntity<?> addProduct(ProReq req, MultipartFile[] images) throws IOException {
        return ResponseEntity.ok().body(new DataResponse(200, "Thành công", productService.addProduct(req, images)));
    }

    @DeleteMapping("/admin/remove/{id}")
    public ResponseEntity<?> removeProduct(@PathVariable Long id) {
        if(productService.removeProduct(id)) {
            return ResponseEntity.ok().body(new DataResponse(200, "Thành công"));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new DataResponse(400, "Lỗi khi xóa"));
    }

    @PutMapping("/admin/update/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable Long id, ProReq req, MultipartFile[] images) throws IOException {
        if(productService.updateProduct(id, req, images)) {
            return ResponseEntity.ok().body(new DataResponse(200, "Thành công"));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new DataResponse(400, "Lỗi khi cập nhật"));
    }
}
