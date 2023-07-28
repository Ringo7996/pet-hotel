package com.example.demo.model.enums;

public enum Status {
    PENDING("pending"),
    CONFIRM("confirmed"),
    CANCEL("cancel"),
    COMPLETED("completed");

    private String value;

    Status(String value) {
        this.value = value;
    }

}
