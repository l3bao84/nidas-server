package com.LeBao.sales.controllers;

import com.LeBao.sales.requests.OrderStatisticRequest;
import com.LeBao.sales.requests.SearchOrderReq;
import com.LeBao.sales.requests.UpdateOrderReq;
import com.LeBao.sales.responses.DataResponse;
import com.LeBao.sales.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/api/v1/admin/orders")
    public ResponseEntity<DataResponse> getOrders(SearchOrderReq req) {
        return ResponseEntity.ok().body(new DataResponse(200, orderService.getAll(req)));
    }

    @GetMapping("/api/v1/admin/orders/{orderStatus}")
    public ResponseEntity<DataResponse> countOrders(@PathVariable String orderStatus) {
        return ResponseEntity.ok().body(new DataResponse(200, orderService.countOrders(orderStatus)));
    }

    @GetMapping("/api/v1/admin/totalItemsSold")
    public ResponseEntity<DataResponse> getItemsSold() {
        return ResponseEntity.ok().body(new DataResponse(200, orderService.getItemsSold()));
    }

    @GetMapping("/api/v1/admin/totalEarning")
    public ResponseEntity<DataResponse> getTotalEarning() {
        return ResponseEntity.ok().body(new DataResponse(200, orderService.getTotalEarnings()));
    }

    @GetMapping("/api/v1/admin/orderStatistic")
    public ResponseEntity<DataResponse> getOrderStatistic(OrderStatisticRequest request) {
        return ResponseEntity.ok().body(new DataResponse(200, orderService.getOrderStatistic(request)));
    }

    @PatchMapping("/api/v1/admin/orders/update")
    public ResponseEntity<?> updateOrder(UpdateOrderReq req) {
        return ResponseEntity.ok().body(new DataResponse(200, orderService.updateOrder(req)));
    }
}
