package com.LeBao.sales.exceptions;

public class CartItemDeletionException extends RuntimeException{
    public CartItemDeletionException(String message) {
        super(message);
    }
}
