package com.LeBao.sales.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class AddressRequest {

    private String fullName;
    private String phoneNumber;
    private String address;
    private String city;
}
