package com.example.demo.service;

import com.example.demo.model.entity.Hotel;
import com.example.demo.model.entity.User;
import com.example.demo.model.request.CreateHotelRequest;
import com.example.demo.model.roombooking.RoomType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface HotelService {

    Page<Hotel> getAllHotelsWithPage(Pageable pageable);

    Hotel findbyId(Integer hotelId);

    List<RoomType> getRoomType(Integer hotelId);

    User createHotel(CreateHotelRequest request);
}
