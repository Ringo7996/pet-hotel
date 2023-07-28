package com.example.demo.model.enums;

public enum Schedule {
    FROM8TO10("8h-10h"),
    FROM10TO12("10h-12h"),
    FROM13TO15("13h-15h"),
    FROM15TO17("15h-17h");

    private String value;

    Schedule(String value) {
        this.value = value;
    }
}
