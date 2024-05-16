package com.LeBao.sales.controllers;

import com.LeBao.sales.requests.OrderRequest;
import com.LeBao.sales.requests.PaymentExecutionRequest;
import com.LeBao.sales.services.CheckoutService;
import com.paypal.base.rest.PayPalRESTException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
public class CheckoutController {

    private final CheckoutService checkoutService;

    @PostMapping()
    public ResponseEntity<?> placeOrder(OrderRequest request) throws PayPalRESTException {
        return ResponseEntity.ok().body(checkoutService.placeOrder(request));
    }

    @PostMapping("/execute-payment")
    public ResponseEntity<?> executePayment(PaymentExecutionRequest request) throws PayPalRESTException {
        return ResponseEntity.ok().body(checkoutService.executePayment(request));
    }

}
