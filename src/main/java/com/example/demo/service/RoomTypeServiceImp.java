package com.example.demo.service;

import com.example.demo.exception.NotFoundException;
import com.example.demo.model.entity.Hotel;
import com.example.demo.model.roombooking.RoomType;
import com.example.demo.repository.HotelRepository;
import com.example.demo.repository.RoomTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoomTypeServiceImp implements RoomTypeService{
    @Autowired
    private RoomTypeRepository roomTypeRepository;

    @Autowired
    HotelRepository hotelRepository;

    @Override
    public List<RoomType> getAllRoomType() {
        return roomTypeRepository.findAll();
    }

    @Override
    public RoomType findById(Integer roomTypeId) {
        return roomTypeRepository.findById(roomTypeId).orElseThrow(()-> new NotFoundException("Room Type not found"));
    }

    @Override
    public List<RoomType> findAllRoomsNotPartOfHotel(Integer id) {
        return  roomTypeRepository.findAllRoomsNotPartOfHotel(id);
    }
}
