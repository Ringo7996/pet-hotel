package com.example.demo.service;

import com.example.demo.model.entity.Hotel;
import com.example.demo.model.entity.User;
import com.example.demo.model.projection.AvailableRoomTypeInfo;
import com.example.demo.model.request.CreateHotelRequest;
import com.example.demo.model.request.HotelRequest;
import com.example.demo.model.roombooking.HotelRoomType;
import com.example.demo.model.roombooking.RoomType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface HotelService {

    void updateInfoHotel (HotelRequest hotelRequest, Integer id);

    Page<Hotel> getAllHotelsWithPage(Pageable pageable);

    Page<Hotel> getHotelsActivityWithPage(Boolean status,Pageable pageable);

    Hotel findById(Integer hotelId);

    List<RoomType> getRoomType(Integer hotelId);

    User createHotel(CreateHotelRequest request);

    Page<Hotel> getMyHotelsWithPage(Pageable pageable, Integer userId);

    List<String> getAllCity();

    List<String> getAllDistrictByCity(String city);

    List<AvailableRoomTypeInfo> getAvailableHotelByDateRange(String district, LocalDate startDay, LocalDate endDay);
    void createHotel(HotelRequest hotelRequest);

    void softDelete(Integer id);
    void activityHotel(Integer id);


}
