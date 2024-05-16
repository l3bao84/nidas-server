package com.LeBao.sales.controllers;

import com.LeBao.sales.requests.AddressRequest;
import com.LeBao.sales.services.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/my-account")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @GetMapping("/get-personal-info")
    public ResponseEntity<?> getPersonalInfo() {
        return ResponseEntity.ok().body(accountService.getPersonalInfo());
    }

    @GetMapping("/addresses")
    public ResponseEntity<?> getAddresses() {
        return ResponseEntity.ok().body(accountService.getShippingAddress());
    }

    @PostMapping("/addresses")
    public ResponseEntity<?> addAddress(@RequestBody AddressRequest request) {
        return ResponseEntity.ok().body(accountService.add(request));
    }

    @PutMapping("/addresses/{id}")
    public ResponseEntity<?> updateAddress(@PathVariable Long id, @RequestBody AddressRequest request) {
        return ResponseEntity.ok().body(accountService.update(id,request));
    }

    @DeleteMapping("/addresses/{id}")
    public ResponseEntity<?> removeAddress(@PathVariable Long id) {
        accountService.removeAddress(id);
        return ResponseEntity.ok().body("Remove successfully");
    }

    @GetMapping("/my-orders")
    public ResponseEntity<?> getOrders(@RequestParam(value = "type", required = false) String type) {
        return ResponseEntity.ok().body(accountService.getOrders(type));
    }

    @PatchMapping("/my-orders/{id}/cancel")
    public ResponseEntity<?> cancelOrder(@PathVariable Long id) {
        return ResponseEntity.ok().body(accountService.cancelOrder(id));
    }
}
