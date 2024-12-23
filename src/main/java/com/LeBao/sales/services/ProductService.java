package com.LeBao.sales.services;

import com.LeBao.sales.entities.Category;
import com.LeBao.sales.entities.Product;
import com.LeBao.sales.repositories.CategoryRepository;
import com.LeBao.sales.repositories.OrderDetailRepository;
import com.LeBao.sales.repositories.ProductRepository;
import com.LeBao.sales.requests.ProReq;
import com.LeBao.sales.requests.SearchProReq;
import com.LeBao.sales.responses.ProductResponse;
import com.LeBao.sales.utils.DataUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    private final CategoryRepository categoryRepository;

    private final StorageService service;

    private final OrderDetailRepository orderDetailRepository;

    public ProductResponse getProduct(Long productId) {
        if(productRepository.findById(productId).isPresent()) {
            Product product = productRepository.findById(productId).get();

            return ProductResponse.builder()
                    .id(product.getProductId())
                    .productName(product.getProductName())
                    .productDescription(product.getProductDescription())
                    .price(product.getPrice())
                    .stock(product.getStock())
                    .pieces(product.getPieces())
                    .images(product.getImages())
                    .categoryId(product.getCategory().getCategoryId())
                    .cartItems(product.getCartItems())
                    .orderDetails(product.getOrderDetails())
                    .reviews(product.getReviews()).build();
        }
        return null;
    }

    public List<Product> getRecommendedProducts() {
        List<Product> products = productRepository.findAll();
        Collections.reverse(products);
        List<Product> recommendedProducts = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            recommendedProducts.add(products.get(i));
        }
        return recommendedProducts;
    }

    public List<Product> getTopPickProducts() {
        return orderDetailRepository.getTopPicksProduct();
    }

    public Page<Product> getAll(SearchProReq req) {
        int pageSize = 10;
        Pageable pageable = PageRequest.of(req.getPage() - 1, pageSize);
        return DataUtils.isNullOrEmpty(req.getKeyword().trim()) ? productRepository.getAll(pageable) : productRepository.search(pageable, req.getKeyword().trim());
    }

    public Product addProduct(ProReq req, MultipartFile[] images) throws IOException {
        Product product = Product.builder()
                .productName(req.getName().trim())
                .productDescription(req.getDesc().trim())
                .price(DataUtils.safeToDouble(req.getPrice().trim()))
                .stock(DataUtils.safeToInt(req.getStock().trim()))
                .pieces(DataUtils.safeToInt(req.getPiece().trim()))
                .images(images != null ? service.upload(images) : new ArrayList<>())
                .category(categoryRepository.findByName(req.getCategory().trim()))
                .build();

        return productRepository.save(product);
    }

    public boolean updateProduct(Long id, ProReq req, MultipartFile[] images) throws IOException {
        Optional<Product> foundProduct = productRepository.findById(id);
        if (foundProduct.isEmpty()) {
            return false;
        }

        Product product = foundProduct.get();
        req.trim();

        if (!DataUtils.safeEquals(product.getProductName(), req.getName())) {
            product.setProductName(req.getName());
        }
        if (!DataUtils.safeEquals(product.getPrice(), req.getPrice())) {
            product.setPrice(DataUtils.safeToDouble(req.getPrice()));
        }
        if (!DataUtils.safeEquals(product.getStock(), req.getStock())) {
            product.setStock(DataUtils.safeToInt(req.getStock()));
        }
        if (!DataUtils.safeEquals(product.getPieces(), req.getPiece())) {
            product.setPieces(DataUtils.safeToInt(req.getPiece()));
        }
        if (!DataUtils.safeEquals(product.getProductDescription(), req.getDesc())) {
            product.setProductDescription(req.getDesc());
        }


        if (!DataUtils.safeEquals(product.getCategory().getCategoryName(), req.getCategory())) {
            Category newCategory = categoryRepository.findByName(req.getCategory());
            if (newCategory != null && !newCategory.equals(product.getCategory())) {
                product.getCategory().getProducts().remove(product);
                newCategory.getProducts().add(product);
                product.setCategory(newCategory);
            }
        }

        if (images != null && images.length > 0) {
            product.setImages(service.upload(images));
        }

        productRepository.save(product);
        return true;
    }


    public boolean removeProduct(Long id) {
        Optional<Product> foundProduct = productRepository.findById(id);

        if (foundProduct.isPresent()) {
            Product product = foundProduct.get();
            if (product.getCategory() != null) {
                Optional<Category> optionalCategory = categoryRepository.findById(product.getCategory().getCategoryId());
                optionalCategory.ifPresent(category -> {
                    category.getProducts().remove(product);
                    categoryRepository.save(category);
                });
            }
            productRepository.deleteById(id);
            boolean stillExists = productRepository.findById(id).isPresent();
            return !stillExists;
        }

        return false;
    }


    public Page<Product> search(String keyword, int page, String sortValue) {
        int pageSize = 8;
        Pageable pageable; Page<Product> products = null;
        if(sortValue != null) {
            if(sortValue.equalsIgnoreCase("Price: Low to High")) {
                pageable = PageRequest.of(page, pageSize, Sort.by(Sort.Order.asc("price")));
                products = productRepository.findByProductNameContainingIgnoreCase(keyword, pageable);
            }else if(sortValue.equalsIgnoreCase("Price: High to Low")) {
                pageable = PageRequest.of(page,pageSize,Sort.by(Sort.Order.desc("price")));
                products = productRepository.findByProductNameContainingIgnoreCase(keyword, pageable);
            }else if(sortValue.equalsIgnoreCase("A-Z")) {
                pageable = PageRequest.of(page,pageSize,Sort.by(Sort.Order.asc("productName")));
                products = productRepository.findByProductNameContainingIgnoreCase(keyword, pageable);
            }else {
                pageable = PageRequest.of(page, pageSize);
                products = productRepository.findByProductNameContainingIgnoreCase(keyword, pageable);
            }
        }

        return products;
    }
}