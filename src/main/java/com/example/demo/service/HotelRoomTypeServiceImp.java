package com.example.demo.service;

import com.example.demo.model.projection.RoomTypeInfo;
import com.example.demo.model.roombooking.HotelRoomType;
import com.example.demo.model.roombooking.RoomType;
import com.example.demo.repository.HotelRoomTypeRepository;
import com.example.demo.repository.RoomBookingRepository;
import com.example.demo.repository.RoomTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HotelRoomTypeServiceImp implements HotelRoomTypeService {
    @Autowired
    private HotelRoomTypeRepository hotelRoomTypeRepository;
    @Autowired
    private RoomTypeRepository roomTypeRepository;

    @Autowired
    private RoomBookingRepository roomBookingRepository;


    @Override
    public List<HotelRoomType> findByRoomType_Id(Integer roomTypeId) {
        return hotelRoomTypeRepository.findByRoomTypeId(roomTypeId);
    }

    @Override
    public List<HotelRoomType> findByHotel_Id(Integer hotelId) {
        return hotelRoomTypeRepository.findByStatusAndHotel_Id(true, hotelId);
    }

    @Override
    public List<HotelRoomType> findAllById(List<Integer> roomTypeId) {
        return hotelRoomTypeRepository.findAllById(roomTypeId);
    }

    @Override
    public List<RoomTypeInfo> getAvailableRoomTypeByHotelId(Integer hotelId) {
//        List<Integer> hotelIds = List.of(hotelId);
//        List<HotelRoomType> hotelRoomTypesByHotels = hotelRoomTypeRepository.findByHotelIdList(hotelIds);
//        List<HotelRoomType> availableHotelRoomTypes = new ArrayList<>();
//
//        // duyệt từng hotel room type, xem trong khoảng thời gian đó đã bị đặt mấy phòng -> nếu còn phòng thì add vào available list
//        for (HotelRoomType hotelRoomType : hotelRoomTypesByHotels) {
//            int bookedRoomNum = roomBookingRepository.countByDateRange(hotelRoomType.getId(), startDay, endDay);
//            System.out.println("hotelRoomTypeid= " + hotelRoomType.getId() + " bookedRoomNum" + bookedRoomNum);
//            if (hotelRoomType.getTotalRoomNumber() > bookedRoomNum) {
//                availableHotelRoomTypes.add(hotelRoomType);
//            }
//        }

        List<HotelRoomType> hotelRoomType = hotelRoomTypeRepository.findAllByStatusAndHotelId(true, hotelId);
        List<Integer> roomTypeIds = hotelRoomType.stream().map(hrt -> hrt.getRoomType().getId()).collect(Collectors.toList());

        return roomTypeRepository.findAllRoomTypeByIds(roomTypeIds);

    }
}
