package com.LeBao.sales.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class OrdersDTO {
    private Long orderId;
    private String status;
    private Double total;
    private String paymentStatus;
    private List<ProductOrderDTO> productOrderDTOList;
}
