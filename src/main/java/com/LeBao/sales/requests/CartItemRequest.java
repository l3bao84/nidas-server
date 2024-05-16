package com.LeBao.sales.requests;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CartItemRequest {

    private long id;
    private int quantity;
}
