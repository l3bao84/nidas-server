package com.LeBao.sales.requests;

import com.LeBao.sales.entities.CartItem;
import lombok.Data;

import java.util.List;

@Data
public class OrderRequest {

    private String status;
    private String paymentStatus;
    private String address;
    private Double total;
    private String shippingMethod;
    private String items;
}
