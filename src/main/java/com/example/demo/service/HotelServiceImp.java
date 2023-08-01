package com.example.demo.service;

import com.example.demo.exception.NotFoundException;
import com.example.demo.model.entity.Hotel;
import com.example.demo.model.entity.User;
import com.example.demo.model.request.CreateHotelRequest;
import com.example.demo.model.roombooking.HotelRoomType;
import com.example.demo.model.roombooking.RoomType;
import com.example.demo.repository.HotelRepository;
import com.example.demo.repository.HotelRoomTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class HotelServiceImp implements HotelService {
    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private HotelRoomTypeRepository hotelRoomTypeRepository;

    @Override
    public Page<Hotel> getAllHotelsWithPage(Pageable pageable) {
        return hotelRepository.findAll(pageable);
    }

    @Override
    public Hotel findById(Integer hotelId) {
        return hotelRepository.findById(hotelId).orElseThrow(() -> new NotFoundException("Hotel with id " + hotelId + " is not found"));
    }

    @Override
    public List<RoomType> getRoomType(Integer hotelId) {
        List<RoomType> roomTypeList = new ArrayList<>();
        List<HotelRoomType> hotelRoomTypes = hotelRoomTypeRepository.findByHotel_Id(hotelId);
        for (HotelRoomType hotelRoomType : hotelRoomTypes) {
            roomTypeList.add(hotelRoomType.getRoomType());
        }
        return roomTypeList;
    }

    @Override
    public User createHotel(CreateHotelRequest request) {
        return null;
    }

    @Override
    public Page<Hotel> getMyHotelsWithPage(Pageable pageable, Integer userId) {
        return hotelRepository.findByStaff_IdOrderById(userId,pageable);
    }


}
