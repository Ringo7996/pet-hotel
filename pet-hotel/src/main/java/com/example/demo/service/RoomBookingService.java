package com.example.demo.service;


import com.example.demo.model.entity.User;
import com.example.demo.model.request.CreateRoomBookingRequest;
import com.example.demo.model.roombooking.RoomBooking;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface RoomBookingService {
    List<RoomBooking> findMyBookings(User user);

    RoomBooking findByIdWithUser(Integer roomBookingId, User user);

    Page<RoomBooking> getAllRoomBookingsWithPage(Pageable pageable);

    RoomBooking makeARoomBooking(CreateRoomBookingRequest request, User user);
}
