package com.LeBao.sales.repositories;

import com.LeBao.sales.entities.OrderDetail;
import com.LeBao.sales.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderDetailRepository extends JpaRepository<OrderDetail,Long> {

    @Query("DELETE FROM OrderDetail o WHERE o.order.id = :orderId")
    int deleteByOrderId(@Param("orderId") Long orderId);

    @Query("select o.product from OrderDetail o group by o.product having sum(o.quantity) >= 3")
    List<Product> getTopPicksProduct();

}
