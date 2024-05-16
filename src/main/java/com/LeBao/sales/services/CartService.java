package com.LeBao.sales.services;

import com.LeBao.sales.entities.*;
import com.LeBao.sales.exceptions.CartItemDeletionException;
import com.LeBao.sales.repositories.CartItemRepository;
import com.LeBao.sales.repositories.CartRepository;
import com.LeBao.sales.repositories.ProductRepository;
import com.LeBao.sales.repositories.UserRepository;
import com.LeBao.sales.requests.CartItemRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;

@Service
@Transactional
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;

    private final CartItemRepository cartItemRepository;

    private final UserService userService;

    private final UserRepository userRepository;

    private final ProductRepository productRepository;

    private final CartItemService cartItemService;

    private Cart createCart(User user) {
        return Cart.builder()
                .user(user)
                .creationDate(LocalDate.now())
                .cartItems(new HashSet<>())
                .build();
    }

    public Product addCartItem(Long id, int quantity) {
        User user = userService.getCurrentUsername();
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        Cart cart = Optional.ofNullable(user.getCart())
                .orElseGet(() -> createCart(user));

        Optional<CartItem> optionalCartItem = cartItemService.findByProductIdAndCartId(product.getProductId(), cart.getCartId());
        if(optionalCartItem.isPresent()) {
            cartItemService.updateCartItem(optionalCartItem.get(), quantity);
        }else {
            CartItem cartItem = cartItemService.createCartItem(cart, product, quantity);
            cart.getCartItems().add(cartItem);
        }


        userRepository.save(user);
        return product;
    }

    public List<CartItem> getCart() {
        User user = userService.getCurrentUsername();
        List<CartItem> cartItems = new ArrayList<>();
        if(user == null) {
            return cartItems;
        }else {
            Cart cart = user.getCart();
            if(cart != null) {
                cartItems = cart.getCartItems().stream().toList();
            }
        }
        return cartItems;
    }

    @Transactional
    public void delCartItem(Long id) {
        try {
            User user = userService.getCurrentUsername();

            boolean removed = user.getCart().getCartItems().removeIf(cartItem -> cartItem.getCartItemId().equals(id));
            cartItemService.removeCartItem(id);
            if (!removed) {
                throw new CartItemDeletionException("Failed to remove cart item");
            }
            userRepository.save(user);
        } catch (Exception e) {
            throw new CartItemDeletionException("Error occurred while deleting cart item: " + e.getMessage());
        }
    }

    public void delCartById(Long id) {
        cartRepository.deleteById(id);
    }

    public void changeCartItemQuantity(CartItemRequest cartItemRequest) {

        CartItem cartItem = cartItemService.findByCartItemId(cartItemRequest.getId())
                        .orElseThrow(() -> new RuntimeException("CartItem not found"));

        cartItemService.updateCartItem(cartItem, cartItemRequest.getQuantity() - cartItem.getQuantity());
    }
}
