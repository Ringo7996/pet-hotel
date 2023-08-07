package com.example.demo.repository;


import com.example.demo.model.roombooking.RoomType;
import com.example.demo.model.projection.RoomTypeInfo;
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
            "  WHERE hrt.roomType = rt AND hrt.hotel.id = :hotelId AND hrt.status = true" +
            ")")
    List<RoomTypeInfo> findAllRoomsNotPartOfHotel(@Param("hotelId") Integer id);


    @Query(nativeQuery = true, value = "SELECT * from room_type WHERE room_type.id in :roomTypeIds AND room_type.status = true ")
    List<RoomTypeInfo> findAllRoomTypeByIds(List<Integer> roomTypeIds);

    @Query(nativeQuery = true, value = "SELECT * from room_type WHERE room_type.id = :id")
    RoomTypeInfo findByIdProjectedBy(Integer id);
}