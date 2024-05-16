package com.LeBao.sales.services.impl;

import com.LeBao.sales.entities.Order;
import com.LeBao.sales.entities.OrderDetail;
import com.LeBao.sales.repositories.OrderRepository;
import com.LeBao.sales.requests.OrderStatisticRequest;
import com.LeBao.sales.requests.SearchOrderReq;
import com.LeBao.sales.requests.SearchProReq;
import com.LeBao.sales.requests.UpdateOrderReq;
import com.LeBao.sales.services.OrderService;
import com.LeBao.sales.utils.DataUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    @Override
    public Long countOrders(String orderStatus) {
        return orderRepository.findAll().stream()
                .filter(order -> order.getOrderStatus().equals(orderStatus))
                .count();
    }

    @Override
    public Integer getItemsSold() {
        return orderRepository.findAll().stream()
                .filter(order -> order.getOrderStatus().equals("COMPLETED"))
                .flatMap(order -> order.getOrderDetails().stream())
                .mapToInt(OrderDetail::getQuantity)
                .sum();
    }

    @Override
    public Double getTotalEarnings() {
        return orderRepository.findAll().stream()
                .filter(order -> order.getOrderStatus().equals("COMPLETED"))
                .mapToDouble(Order::getTotalAmount)
                .sum();
    }

    @Override
    public Map<LocalDate, Long> getOrderStatistic(OrderStatisticRequest request) {
        LocalDate fromDate = DataUtils.safeToLocalDate(request.getFromDate());
        LocalDate toDate = DataUtils.safeToLocalDate(request.getToDate());

        Map<LocalDate, Long> ordersCountByDate = orderRepository.findCompletedOnDateRange(fromDate, toDate).stream()
                .collect(Collectors.groupingBy(
                        Order::getOrderDate,
                        Collectors.counting()
                ));

        Map<LocalDate, Long> rs = new TreeMap<>();
        for (LocalDate date = fromDate; !Objects.requireNonNull(date).isAfter(toDate); date = date.plusDays(1)) {
            rs.put(date, ordersCountByDate.getOrDefault(date, 0L));
        }

        return rs;
    }

    @Override
    public Page<Order> getAll(SearchOrderReq req) {
        int pageSize = 10;
        Pageable pageable = PageRequest.of(req.getPage() - 1, pageSize);
        return DataUtils.isNullOrZero(req.getId()) ? orderRepository.getAll(pageable) : orderRepository.search(pageable, req.getId());
    }

    @Override
    @Transactional
    public Order updateOrder(UpdateOrderReq req) {
        Optional<Order> order = orderRepository.findById(req.getId());
        order.ifPresent(value -> value.setOrderStatus(req.getStatus().trim()));
        return order.orElse(null);
    }

}
