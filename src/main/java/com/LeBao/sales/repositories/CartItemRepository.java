package com.LeBao.sales.repositories;

import com.LeBao.sales.entities.CartItem;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    @Query("SELECT c FROM CartItem c WHERE c.product.productId = :id AND c.cart.cartId = :cartId")
    Optional<CartItem> findByProductIdAndCartId(@Param("id") Long productId, @Param("cartId") Long cartId);

    @Modifying
    @Transactional
    @Query("DELETE FROM CartItem ci WHERE ci.cartItemId = :id")
    void removeByCartItemId(@Param("id") Long cartItemId);

}
