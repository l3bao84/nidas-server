package com.LeBao.sales.services;

import com.LeBao.sales.entities.*;
import com.LeBao.sales.repositories.CartItemRepository;
import com.LeBao.sales.repositories.OrderRepository;
import com.LeBao.sales.requests.OrderRequest;
import com.LeBao.sales.requests.PaymentExecutionRequest;
import com.LeBao.sales.responses.DataResponse;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor
public class CheckoutService {

    private final UserService userService;
    private final OrderRepository orderRepository;
    private final EmailService emailService;
    private final CartItemRepository cartItemRepository;
    private final PayPalService payPalService;
    private static final String CANCEL_URL = "/checkout?pay=cancel";

    public DataResponse placeOrder(OrderRequest request) throws PayPalRESTException {
        String[] ids = request.getItems().split(",");

        Order order = Order.builder()
                .user(userService.getCurrentUsername())
                .orderDate(LocalDate.now())
                .shippingAddress(request.getAddress())
                .shippingMethod(request.getShippingMethod())
                .totalAmount(request.getTotal())
                .paymentStatus(request.getPaymentStatus())
                .orderStatus(request.getStatus())
                .build();

        List<OrderDetail> orderDetails = Arrays.stream(ids).map(id -> cartItemRepository.findById(Long.parseLong(id)).orElse(null)).filter(Objects::nonNull)
                .map(cartItem -> OrderDetail.builder()
                        .order(order)
                        .quantity(cartItem.getQuantity())
                        .unitPrice(cartItem.getPrice())
                        .product(cartItem.getProduct())
                        .build()).toList();

        order.setOrderDetails(orderDetails);
        DataResponse response = new DataResponse();

        if(order.getPaymentStatus().equals("PayPal")) {
            Payment payment = payPalService.createPayment(order.getTotalAmount(), "http://localhost:3000" + CANCEL_URL, "http://localhost:3000");
            for (Links link:payment.getLinks()) {
                    if(link.getRel().equals("approval_url")) {
                        response = DataResponse.builder()
                                .code(201)
                                .message("Pay by PayPal")
                                .data(order)
                                .link(link.getHref())
                                .build();
                        break;
                    }
                }
            order.setPaymentStatus(payment.getId());
            orderRepository.save(order);
        }else {
            orderRepository.save(order);
            emailService.sendEmail(order);
            response = DataResponse.builder()
                .code(200)
                .message("Order successfully")
                .data(order)
                .build();
        }
        return response;
    }

    public DataResponse executePayment(PaymentExecutionRequest request) throws PayPalRESTException {
        DataResponse response = new DataResponse();
        Optional<Order> optionalOrder = orderRepository.findByPaymentStatus(request.getPaymentId());
        if(optionalOrder.isPresent()) {
            Order order = optionalOrder.get();

            Payment payment = payPalService.executePayment(request.getPaymentId(), request.getPayerId());
            if (payment.getState().equals("approved")) {
                order.setPaymentStatus("PayPal");
                orderRepository.save(order);
                emailService.sendEmail(order);
                response = DataResponse.builder()
                        .code(200)
                        .data(order)
                        .build();
            }
        }

        return response;
    }

}
