package com.LeBao.sales.services;

import com.LeBao.sales.DTO.OrdersDTO;
import com.LeBao.sales.DTO.PersonalInfoDTO;
import com.LeBao.sales.DTO.ProductOrderDTO;
import com.LeBao.sales.entities.Order;
import com.LeBao.sales.entities.ShippingAddress;
import com.LeBao.sales.entities.User;
import com.LeBao.sales.repositories.OrderRepository;
import com.LeBao.sales.repositories.ShippingAddressRepository;
import com.LeBao.sales.repositories.UserRepository;
import com.LeBao.sales.requests.AddressRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final UserRepository userRepository;

    private final UserService userService;

    private final ShippingAddressRepository shippingAddressRepository;

    private final OrderRepository orderRepository;

    public PersonalInfoDTO getPersonalInfo() {

        User user = userService.getCurrentUsername();
        PersonalInfoDTO personalInfoDTO = null;
        if(user != null) {
            personalInfoDTO = userRepository.getPersonalInfo(user.getEmail());
            personalInfoDTO.setAddress(user.getShippingAddresses().stream().toList().get(0).getAddress());
            personalInfoDTO.setPhonenumber(user.getShippingAddresses().stream().toList().get(0).getPhoneNumber());
        }

        return personalInfoDTO;
    }

    public List<ShippingAddress> getShippingAddress() {
        return userService.getCurrentUsername().getShippingAddresses().stream().toList();
    }

    public ShippingAddress add(AddressRequest request) {
        User user = userService.getCurrentUsername();
        ShippingAddress address = new ShippingAddress();
        if(user != null) {
            address = ShippingAddress.builder()
                    .fullName(request.getFullName())
                    .phoneNumber(request.getPhoneNumber())
                    .address(request.getAddress())
                    .city(request.getCity())
                    .user(user)
                    .build();

            shippingAddressRepository.save(address);
            user.getShippingAddresses().add(address);
        }
        return address;
    }

    public ShippingAddress update(Long id, AddressRequest request) {
        ShippingAddress address = null;
        if(shippingAddressRepository.findById(id).isPresent()) {
            address = shippingAddressRepository.findById(id).get();

            if(request.getFullName() != null
                    && !request.getFullName().trim().isEmpty()
                    && !request.getFullName().trim().equalsIgnoreCase(address.getFullName())) {
                address.setFullName(request.getFullName().trim());
            }

            if(request.getPhoneNumber() != null
                    && !request.getPhoneNumber().trim().isEmpty()
                    && !request.getPhoneNumber().trim().equalsIgnoreCase(address.getPhoneNumber())) {
                address.setPhoneNumber(request.getPhoneNumber().trim());
            }

            if(request.getAddress() != null
                    && !request.getAddress().trim().isEmpty()
                    && !request.getAddress().trim().equalsIgnoreCase(address.getAddress())) {
                address.setAddress(request.getAddress().trim());
            }

            if(request.getCity() != null
                    && !request.getCity().trim().isEmpty()
                    && !request.getCity().trim().equalsIgnoreCase(address.getCity())) {
                address.setCity(request.getCity().trim());
            }
            shippingAddressRepository.save(address);
        }
        return address;
    }

    public void removeAddress(Long id) {
        User user = userService.getCurrentUsername();
        if(shippingAddressRepository.findById(id).isPresent()) {
            ShippingAddress address = shippingAddressRepository.findById(id).get();
            shippingAddressRepository.deleteById(id);
            user.getShippingAddresses().remove(address);
            userRepository.save(user);
        }
    }

    public List<OrdersDTO> getOrders(String type) {
        User user = userService.getCurrentUsername();
        Set<Order> orders = orderRepository.findByStatusAndUserId(user.getUserId(), type);
        if(type.equals("ALL")) {
            orders = user.getOrders();
        }
        return orders.stream()
                .map(order -> {

                    List<ProductOrderDTO> productOrderDTOList = order.getOrderDetails().stream()
                            .map(orderDetail -> ProductOrderDTO.builder()
                                    .quantity((long) orderDetail.getQuantity())
                                    .image(orderDetail.getProduct().getImages().get(0))
                                    .price(orderDetail.getUnitPrice())
                                    .name(orderDetail.getProduct().getProductName())
                                    .build()
                            ).toList();

                    return OrdersDTO.builder()
                            .orderId(order.getOrderId())
                            .status(order.getOrderStatus())
                            .total(order.getTotalAmount())
                            .paymentStatus(order.getPaymentStatus())
                            .productOrderDTOList(productOrderDTOList)
                            .build();
                })
                .toList();
    }

    @Transactional
    public Order cancelOrder(Long id) {
        Optional<Order> order = orderRepository.findById(id);
        order.ifPresent(value -> value.setOrderStatus("CANCELLED"));
        return order.orElse(null);
    }
}
