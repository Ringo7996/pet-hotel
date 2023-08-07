package com.example.demo.repository;


import com.example.demo.model.roombooking.RoomBooking;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface RoomBookingRepository extends JpaRepository<RoomBooking, Integer> {

    // lấy ra record có ngày bắt đầu hoặc ngày kết thúc truyền vào nằm trong khoảng start-end của record booking
    // -> là những record chứa hotel room type đang bị booking trong khoảng ngày đó
    @Query(nativeQuery = true, value =
            "SELECT COUNT(*) FROM room_booking WHERE " +
                    "((:startDay >= start_date AND :startDay <= end_date) OR (:endDay >= start_date AND :endDay <= end_date)) " +
                    "AND hotel_room_type_id = :hotelRoomTypeId")
    int countByDateRange(Integer hotelRoomTypeId, LocalDate startDay, LocalDate endDay);


    @Query(nativeQuery = true, value = "SELECT * FROM room_booking WHERE room_booking.hotel_room_type_id IN :hotelRoomTypeIds")
    Page<RoomBooking> findByHotelRoomTypeIdsOrderById(List<Integer> hotelRoomTypeIds, Pageable pageable);

    int countByPetIdAndEndDateGreaterThanEqual(Integer petId, LocalDate now);


    @Query(nativeQuery = true, value = "SELECT COUNT(*) FROM room_booking " +
            "WHERE hotel_room_type_id IN :hotelRoomTypeIds " +
            "AND end_date >= :now")
    int countByHotelRoomTypeIdInAndEndDateGreaterThanEqual(List<Integer> hotelRoomTypeIds, LocalDate now);

    int countByUserIdAndEndDateGreaterThanEqual(Integer userId, LocalDate now);
}

