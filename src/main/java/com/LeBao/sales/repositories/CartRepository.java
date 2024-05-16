package com.LeBao.sales.repositories;

import com.LeBao.sales.entities.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
}
