package com.LeBao.sales.services;

import com.LeBao.sales.entities.Order;
import com.LeBao.sales.requests.OrderStatisticRequest;
import com.LeBao.sales.requests.SearchOrderReq;
import com.LeBao.sales.requests.UpdateOrderReq;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.Map;

public interface OrderService {

    Long countOrders(String orderStatus);

    Integer getItemsSold();
    Double getTotalEarnings();

    Map<LocalDate, Long> getOrderStatistic(OrderStatisticRequest request);

    Page<Order> getAll(SearchOrderReq req);

    Order updateOrder(UpdateOrderReq req);
}
