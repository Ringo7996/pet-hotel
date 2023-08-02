package com.example.demo.service;

import com.example.demo.exception.NotFoundException;
import com.example.demo.model.entity.Hotel;
import com.example.demo.model.entity.User;
import com.example.demo.model.request.CreateHotelRequest;
import com.example.demo.model.roombooking.HotelRoomType;
import com.example.demo.model.roombooking.RoomType;
import com.example.demo.repository.HotelRepository;
import com.example.demo.repository.HotelRoomTypeRepository;
import com.example.demo.repository.RoomBookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HotelServiceImp implements HotelService {
    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private HotelRoomTypeRepository hotelRoomTypeRepository;

    @Autowired
    private RoomBookingRepository roomBookingRepository;

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
        return hotelRepository.findByStaffIdOrderById(userId, pageable);
    }

    @Override
    public List<String> getAllCity() {
        return hotelRepository.getAllCity();
    }

    @Override
    public List<String> getAllDistrictByCity(String city) {
        return hotelRepository.getAllDistrictByCity(city);
    }


    @Override
    public List<Hotel> getAvailableHotelByDateRange(String district, LocalDate startDay, LocalDate endDay) {

        List<Hotel> hotels = hotelRepository.getAllHotelByDistrict(district);

        List<Integer> hotelIds = hotels.stream().map(hotel -> hotel.getId()).collect(Collectors.toList());
        System.out.println("lisst hotel ID= " + hotelIds);

        // lấy các hotel room type theo các hotel truyền vào (đã lọc theo thành phố/quận)
        List<HotelRoomType> hotelRoomTypesByHotels = hotelRoomTypeRepository.findByHotelIdList(hotelIds);
        System.out.println("hotelRoomTypesByHotels= " + hotelRoomTypesByHotels.size());

        List<HotelRoomType> availableHotelRoomTypes = new ArrayList<>();

        // duyệt từng hotel room type, xem trong khoảng thời gian đó đã bị đặt mấy phòng -> nếu còn phòng thì add vào available list
        for (HotelRoomType hotelRoomType : hotelRoomTypesByHotels) {
           int bookedRoomNum = roomBookingRepository.countByDateRange(hotelRoomType.getId(),startDay,endDay);
            System.out.println("hotelRoomTypeid= " + hotelRoomType.getId() + " bookedRoomNum" + bookedRoomNum);
           if (hotelRoomType.getTotalRoomNumber() > bookedRoomNum){
               availableHotelRoomTypes.add(hotelRoomType);
           }
        }

        System.out.println(availableHotelRoomTypes);
        List<Integer> availHotelRoomTypeIds = availableHotelRoomTypes.stream().map(hrt -> hrt.getId()).collect(Collectors.toList());
        List<Hotel> availableHotels = hotelRepository.findByHotelRoomTypeIdList(availHotelRoomTypeIds);

        return availableHotels;
    }


}
