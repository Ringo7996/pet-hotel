package com.example.demo.model.enums;

public enum PaymentType {
    CASH("cash"),
    ZALO("zalo");

    private String value;

    PaymentType(String value) {
        this.value = value;
    }

}
