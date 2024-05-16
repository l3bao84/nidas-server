package com.LeBao.sales.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ProductOrderDTO {
    private Double price;
    private String image;
    private String name;
    private Long quantity;
}
