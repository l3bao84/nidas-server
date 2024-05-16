package com.LeBao.sales.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class PersonalInfoDTO {
    private String fullname;
    private String email;
    private String address;
    private String phonenumber;
    private Long numberOfOder;

    public PersonalInfoDTO(String fullname, String email, Long numberOfOder) {
        this.fullname = fullname;
        this.email = email;
        this.numberOfOder = numberOfOder;
    }
}
