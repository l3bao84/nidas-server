package com.LeBao.sales.controllers;

import com.LeBao.sales.exceptions.CartItemDeletionException;
import com.LeBao.sales.requests.CartItemRequest;
import com.LeBao.sales.services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/carts")
public class CartController {

    private final CartService cartService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping()
    public ResponseEntity<?> getCart() {
        return ResponseEntity.ok().body(cartService.getCart());
    }

    @PostMapping()
    public ResponseEntity<?> addCartItem(@RequestBody CartItemRequest cartItemRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(cartService.addCartItem(cartItemRequest.getId(), cartItemRequest.getQuantity()));
    }

    @DeleteMapping("/{cartItemId}")
    public ResponseEntity<?> removeCartItem(@PathVariable Long cartItemId) {
        cartService.delCartItem(cartItemId);
        return ResponseEntity.ok().body("Remove cart item successfully");
    }

    @PatchMapping()
    public ResponseEntity<?> changeCartItemQuantity(@RequestBody CartItemRequest cartItemRequest) {
        cartService.changeCartItemQuantity(cartItemRequest);
        return ResponseEntity.ok().body("Update cart item quantity successfully");
    }

    @ExceptionHandler(CartItemDeletionException.class)
    public ResponseEntity<Object> handleCartItemDeletionException(CartItemDeletionException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
}
