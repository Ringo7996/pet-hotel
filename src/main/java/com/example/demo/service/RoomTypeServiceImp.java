package com.example.demo.service;

import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.NotFoundException;
import com.example.demo.model.projection.RoomTypeInfo;
import com.example.demo.model.roombooking.HotelRoomType;
import com.example.demo.model.roombooking.RoomType;


import com.example.demo.repository.HotelRepository;
import com.example.demo.repository.HotelRoomTypeRepository;
import com.example.demo.repository.RoomBookingRepository;
import com.example.demo.repository.RoomTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoomTypeServiceImp implements RoomTypeService {
    @Autowired
    private RoomTypeRepository roomTypeRepository;

    @Autowired
    private HotelRoomTypeRepository hotelRoomTypeRepository;

    @Autowired
    private RoomBookingRepository roomBookingRepository;


    @Autowired
    private HotelRepository hotelRepository;

    @Override
    public List<RoomType> getAllRoomType() {
        return roomTypeRepository.findAll();
    }

    @Override
    public RoomType findById(Integer roomTypeId) {
        return roomTypeRepository.findById(roomTypeId).orElseThrow(() -> new NotFoundException("Room Type not found"));
    }

    @Override
    public List<RoomTypeInfo> findAllRoomsNotPartOfHotel(Integer id) {
        return roomTypeRepository.findAllRoomsNotPartOfHotel(id);
    }

    @Override
    public void deleteRoomType(Integer roomTypeId) {
        RoomType roomType = roomTypeRepository.findById(roomTypeId).orElseThrow(() -> new NotFoundException("Room Type not found"));
        if (!isRoomTypeBeingUsed(roomTypeId)) {
            roomType.setStatus(false);
            roomTypeRepository.save(roomType);
        } else {
            throw new BadRequestException("Cannot delete room type because this room type is being use");
        }

    }

    private boolean isRoomTypeBeingUsed(Integer roomTypeId) {
        List<HotelRoomType> hotelRoomTypes = hotelRoomTypeRepository.findByRoomTypeId(roomTypeId);
        List<Integer> hotelRoomTypeIds = hotelRoomTypes.stream().map(hrt -> hrt.getId()).collect(Collectors.toList());

        int bookingCount = roomBookingRepository.countByHotelRoomTypeIdInAndEndDateGreaterThanEqual(hotelRoomTypeIds, LocalDate.now());
        System.out.println(bookingCount);

        return bookingCount > 0;
    }


}
