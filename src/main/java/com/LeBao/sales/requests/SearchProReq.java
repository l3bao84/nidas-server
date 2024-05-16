package com.LeBao.sales.requests;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchProReq {

    private String keyword;
    private int page;
}
