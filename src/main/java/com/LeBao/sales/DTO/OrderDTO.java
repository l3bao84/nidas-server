package com.LeBao.sales.DTO;


import jakarta.persistence.Lob;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

@ToString
public class OrderDTO {

    private String shippingAddress;
    private Double totalAmount;
    private String paymentStatus;

    private String shippingMethod;
    //private String orderStatus; // Preparing the order


    public OrderDTO() {
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getShippingMethod() {
        return shippingMethod;
    }

    public void setShippingMethod(String shippingMethod) {
        this.shippingMethod = shippingMethod;
    }
}
