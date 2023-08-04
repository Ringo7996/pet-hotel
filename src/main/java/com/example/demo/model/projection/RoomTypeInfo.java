package com.example.demo.model.projection;

import java.util.List;

public interface RoomTypeInfo {
    Integer getId();

    String getDescription();

    String getName();

    Double getPrice();

    List<HotelRoomTypeInfo> getHotelRoomTypes();

    interface HotelRoomTypeInfo {
        Integer getId();

        Integer getTotalRoomNumber();

    }
}
