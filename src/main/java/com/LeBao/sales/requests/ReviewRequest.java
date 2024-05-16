package com.LeBao.sales.requests;

import lombok.Data;

@Data
public class ReviewRequest {
    private Long productId;
    private int rate;
    private String content;
}
