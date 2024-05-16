package com.LeBao.sales.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DataResponse {
    private int code;
    private String message;
    private Object data;
    private String link;

    public DataResponse(int code, Object data) {
        this.code = code;
        this.data = data;
    }

    public DataResponse(int code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public DataResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
