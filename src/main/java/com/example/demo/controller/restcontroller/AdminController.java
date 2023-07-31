package com.example.demo.controller.restcontroller;


import com.example.demo.model.roombooking.RoomBooking;
import com.example.demo.service.PetService;
import com.example.demo.service.RoomBookingService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("api/v1/admin")
@PreAuthorize("hasAnyRole('ROLE_ROOT_ADMIN', 'ROLE_ADMIN')")
public class AdminController {
    @Autowired
    private RoomBookingService roomBookingService;

    @Autowired
    private UserService userService;

    @Autowired
    private PetService petService;

    @GetMapping("/room-bookings")
    @PreAuthorize("hasRole('ROLE_ROOT_ADMIN')")
    public Page<RoomBooking> getAllRoomBooking(Pageable pageable) {
        return roomBookingService.getAllRoomBookingsWithPage(pageable);
    }

}
