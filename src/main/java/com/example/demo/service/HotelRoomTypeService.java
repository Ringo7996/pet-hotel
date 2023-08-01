package com.example.demo.service;

import com.example.demo.model.roombooking.HotelRoomType;

import java.util.List;

public interface HotelRoomTypeService {
    List<HotelRoomType> findByRoomType_Id(Integer roomTypeId);

    List<HotelRoomType> findByHotel_Id(Integer hotelId);
}
