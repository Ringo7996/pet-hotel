package com.example.demo.service;

import com.example.demo.model.roombooking.RoomType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RoomTypeService {
    List<RoomType> getAllRoomType();

    RoomType findById(Integer roomTypeId);

    List<RoomType> findAllRoomsNotPartOfHotel(Integer id);
}
