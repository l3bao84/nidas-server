package com.LeBao.sales.services;

import com.LeBao.sales.entities.Order;
import com.LeBao.sales.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class OrderStatusUpdater {

    @Autowired
    private OrderRepository orderRepository;


    @Scheduled(fixedRate = 24 * 60 * 60 * 1000) // Chạy mỗi ngày
    public void updateOrderStatus() {
        LocalDate twoDaysAgo = LocalDate.now().minusDays(2);
        List<Order> ordersToUpdate = orderRepository.findByOrderDateBeforeAndOrderStatus("Preparing the order", twoDaysAgo);

        for (Order order : ordersToUpdate) {
            order.setOrderStatus("Order is being shipped");
            orderRepository.save(order);
        }
    }
}
