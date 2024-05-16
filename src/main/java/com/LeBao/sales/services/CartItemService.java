package com.LeBao.sales.services;

import com.LeBao.sales.entities.Cart;
import com.LeBao.sales.entities.CartItem;
import com.LeBao.sales.entities.Product;
import com.LeBao.sales.repositories.CartItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartItemService {

    private final CartItemRepository cartItemRepository;

    @Autowired
    public CartItemService(CartItemRepository cartItemRepository) {
        this.cartItemRepository = cartItemRepository;
    }

    public CartItem createCartItem(Cart cart, Product product, int quantity) {
        return CartItem.builder()
                .cart(cart)
                .product(product)
                .price(product.getPrice())
                .quantity(quantity)
                .build();
    }

    public Optional<CartItem> findByCartItemId(long cartItemId) {
        return cartItemRepository.findById(cartItemId);
    }

    public Optional<CartItem> findByProductIdAndCartId(long productId, long cartId) {
        return cartItemRepository.findByProductIdAndCartId(productId, cartId);
    }

    public void updateCartItem(CartItem cartItem, int quantityToAdd) {
        int updatedQuantity = Math.min(cartItem.getQuantity() + quantityToAdd, 5);
        cartItem.setQuantity(updatedQuantity);
        cartItemRepository.save(cartItem);
    }

    public void removeCartItem(Long cartItemId) {
        cartItemRepository.removeByCartItemId(cartItemId);
    }
}
