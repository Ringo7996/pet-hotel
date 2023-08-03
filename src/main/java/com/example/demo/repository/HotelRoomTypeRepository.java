package com.example.demo.repository;


import com.example.demo.model.roombooking.HotelRoomType;
import com.example.demo.model.roombooking.RoomType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface HotelRoomTypeRepository extends JpaRepository<HotelRoomType, Integer> {
    List<HotelRoomType> findByHotel_Id(Integer hotelId);

    List<HotelRoomType> findByRoomTypeId(Integer roomTypeId);

    @Query(nativeQuery = true, value = "SELECT * FROM hotel_room_type WHERE hotel_room_type.hotel_id IN :hotelIds")
    List<HotelRoomType> findByHotelIdList(List<Integer> hotelIds);

    @Query("SELECT COUNT(*) FROM HotelRoomType hrt WHERE hrt.hotel.id  =:hotelId   AND hrt.roomType.id =:roomTypeId")
    Integer isExits (@Param("hotelId") Integer hotelId,
                     @Param("roomTypeId") Integer roomTypeId);

    @Transactional
    @Modifying
    @Query("UPDATE HotelRoomType hrt SET hrt.totalRoomNumber =:num WHERE hrt.hotel.id =:hotelId AND hrt.roomType.id =:roomTypeId")
    void updateTotalRoomNumber (@Param("hotelId") Integer hotelId,
                                @Param("roomTypeId") Integer roomTypeId,
                                @Param("num") Integer num);
    @Transactional
    @Modifying
    @Query("DELETE FROM HotelRoomType hrt WHERE hrt.hotel.id = :hotelId")
    void deleteByHotelId (@Param("hotelId") Integer hotelId);

}