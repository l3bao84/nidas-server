package com.LeBao.sales.requests;

import lombok.Data;

@Data
public class PaymentExecutionRequest {
    private String paymentId;
    private String payerId;
}
