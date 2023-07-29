package com.example.demo.controller.restcontroller;

import com.example.demo.model.entity.User;
import com.example.demo.model.request.CreateRoomBookingRequest;
import com.example.demo.model.roombooking.RoomBooking;
import com.example.demo.security.AuthenticationFacade;
import com.example.demo.service.PetService;
import com.example.demo.service.RoomBookingService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/room-bookings")
@PreAuthorize("hasAnyRole('ROLE_ROOT_ADMIN', 'ROLE_HOTEL_STAFF', 'ROLE_HOTEL_ADMIN')")
public class RoomBookingController {
    @Autowired
    private RoomBookingService roomBookingService;

    @Autowired
    private UserService userService;

    @Autowired
    private PetService petService;

    @Autowired
    private AuthenticationFacade authenticationFacade;


    public User getUser() {
        Authentication authentication = authenticationFacade.getAuthentication();
        return userService.findByEmail((String) authentication.getPrincipal());
    }

    @GetMapping("/")
    public Page<RoomBooking> getAllRoomBooking(Pageable pageable) {
        return roomBookingService.getAllRoomBookingsWithPage(pageable);
    }

    @PatchMapping("/make-booking")
    public RoomBooking makeARoomBooking(@RequestParam CreateRoomBookingRequest request) {
        Integer userId = getUser().getId();

        return roomBookingService.makeARoomBooking(request, getUser());
    }
}
