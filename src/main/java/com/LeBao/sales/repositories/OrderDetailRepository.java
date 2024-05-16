package com.LeBao.sales.repositories;

import com.LeBao.sales.entities.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OrderDetailRepository extends JpaRepository<OrderDetail,Long> {

    @Query("DELETE FROM OrderDetail o WHERE o.order.id = :orderId")
    int deleteByOrderId(@Param("orderId") Long orderId);
}
