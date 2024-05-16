package com.LeBao.sales.responses;

import com.LeBao.sales.entities.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductResponse {

    private Long id;
    private String productName;
    private String productDescription;
    private Double price;
    private Integer stock;
    private Integer pieces;
    private List<String> images;
    private Long categoryId;
    private Set<CartItem> cartItems;
    private Set<OrderDetail> orderDetails;
    private Set<Review> reviews;
}
