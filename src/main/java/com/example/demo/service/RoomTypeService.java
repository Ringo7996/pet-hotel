package com.example.demo.service;

import com.example.demo.model.roombooking.RoomType;

import java.util.List;

public interface RoomTypeService {
    List<RoomType> getAllRoomType();

    RoomType findById(Integer roomTypeId);
}
