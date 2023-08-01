package com.example.demo.repository;


import com.example.demo.model.roombooking.HotelRoomType;
import com.example.demo.model.roombooking.RoomType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HotelRoomTypeRepository extends JpaRepository<HotelRoomType, Integer> {
    List<HotelRoomType> findByHotel_Id(Integer hotelId);

    List<HotelRoomType> findByRoomTypeId(Integer roomTypeId);
}