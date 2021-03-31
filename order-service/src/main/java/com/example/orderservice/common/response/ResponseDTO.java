package com.example.orderservice.common.response;

import java.io.Serializable;

public class ResponseDTO implements Serializable {
    private String message;

    public ResponseDTO(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

}