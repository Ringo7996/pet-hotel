package com.example.demo.repository;


import com.example.demo.model.entity.Hotel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Integer> {
    Page<Hotel> findByStaffIdOrderById(Integer userId, Pageable pageable);

    @Query(nativeQuery = true, value = "SELECT DISTINCT city FROM hotel ")
    List<String> getAllCity();

    @Query(nativeQuery = true, value = "SELECT DISTINCT district FROM hotel WHERE hotel.city = :city")
    List<String> getAllDistrictByCity(String city);

    @Query(nativeQuery = true, value = "SELECT * FROM hotel WHERE hotel.district = :district")
    List<Hotel> getAllHotelByDistrict(String district);

    @Query(nativeQuery = true, value = "SELECT DISTINCT hotel.* FROM hotel JOIN hotel_room_type " +
            "WHERE hotel.id = hotel_room_type.hotel_id AND hotel_room_type.id IN :hotelRoomTypeIds")
    List<Hotel> findByHotelRoomTypeIdList(List<Integer> hotelRoomTypeIds);


    List<Hotel> findByStaffIdOrderById(Integer userId);
}