package com.example.demo.service;

import com.example.demo.model.projection.RoomTypeInfo;
import com.example.demo.model.roombooking.HotelRoomType;
import com.example.demo.model.roombooking.RoomType;

import java.util.List;

public interface HotelRoomTypeService {
    List<HotelRoomType> findByRoomType_Id(Integer roomTypeId);

    List<HotelRoomType> findByHotel_Id(Integer hotelId);

    List<HotelRoomType> findAllById(List<Integer> roomTypeId);

    List<RoomTypeInfo> getAvailableRoomTypeByHotelId(Integer hotelId);
}
