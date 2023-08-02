package com.example.demo.model.enums;

public enum Status {
    PENDING("pending"),
    CONFIRMED("confirmed"),
    CANCELED("canceled"),
    COMPLETED("completed"),
    DELETED("deleted");

    private String value;

    Status(String value) {
        this.value = value;
    }

}
