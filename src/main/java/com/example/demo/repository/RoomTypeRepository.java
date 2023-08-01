package com.example.demo.repository;


import com.example.demo.model.roombooking.RoomType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomTypeRepository extends JpaRepository<RoomType, Integer> {

    @Query("SELECT rt FROM RoomType rt " +
            "WHERE NOT EXISTS (" +
            "  SELECT hrt FROM HotelRoomType hrt " +
            "  WHERE hrt.roomType = rt AND hrt.hotel.id = :hotelId" +
            ")")
    List<RoomType> findAllRoomsNotPartOfHotel(@Param("hotelId") Integer id);
}