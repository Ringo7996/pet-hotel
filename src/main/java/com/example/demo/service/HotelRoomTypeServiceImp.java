package com.example.demo.service;

import com.example.demo.model.roombooking.HotelRoomType;
import com.example.demo.repository.HotelRoomTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HotelRoomTypeServiceImp implements HotelRoomTypeService{
    @Autowired
    private HotelRoomTypeRepository hotelRoomTypeRepository;

    @Override
    public List<HotelRoomType> findByRoomType_Id(Integer roomTypeId) {
        return hotelRoomTypeRepository.findByRoomTypeId(roomTypeId);
    }

    @Override
    public List<HotelRoomType> findByHotel_Id(Integer hotelId) {
        return hotelRoomTypeRepository.findByHotel_Id(hotelId);
    }
}
