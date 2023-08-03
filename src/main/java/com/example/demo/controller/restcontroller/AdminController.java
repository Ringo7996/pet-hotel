package com.example.demo.controller.restcontroller;


import com.example.demo.model.entity.User;
import com.example.demo.model.request.CreateUserRequest;
import com.example.demo.model.request.UpdateUserRequest;
import com.example.demo.model.roombooking.RoomBooking;
import com.example.demo.service.PetService;
import com.example.demo.service.RoomBookingService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/admin")
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

    @PostMapping("/create-user")
    @PreAuthorize("hasRole('ROLE_ROOT_ADMIN')")
    public ResponseEntity<?> createUser(@ModelAttribute CreateUserRequest userRequest){

        try {
            userService.createUserByAdmin(userRequest);
        }catch (Exception e){
            System.out.println(e.toString());
            return ResponseEntity.badRequest().body(e.toString());
        }
        return  ResponseEntity.ok("Create success");

    }
    @PostMapping("/update-user/{id}")
    @PreAuthorize("hasRole('ROLE_ROOT_ADMIN')")
    public  ResponseEntity<?> updateUser(@ModelAttribute UpdateUserRequest userRequest,@PathVariable("id") Integer id){
        try {
            userService.updateUserByAdmin(userRequest,id);
        }catch (Exception e){
            System.out.println(e.toString());
            return ResponseEntity.badRequest().body(e.toString());
        }
        return  ResponseEntity.ok("Create success");
    }

    @GetMapping("/get-admin-not-part-of-hotel/{id}")
    @PreAuthorize("hasRole('ROLE_ROOT_ADMIN')")
    public  ResponseEntity<?> getAdminNotPartOfHotel(@PathVariable("id") Integer id){
        try {
            List<User> users = userService.getAdminNotPartOfHotel(id);
            return ResponseEntity.ok(users);
        }catch (Exception e){
            System.out.println(e.toString());
            return ResponseEntity.badRequest().body(e.toString());
        }
    }

}
