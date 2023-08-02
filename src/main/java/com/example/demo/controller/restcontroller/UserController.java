package com.example.demo.controller.restcontroller;


import com.example.demo.model.entity.Pet;
import com.example.demo.model.entity.User;
import com.example.demo.model.request.CreateUserRequest;
import com.example.demo.model.roombooking.RoomBooking;
import com.example.demo.security.AuthenticationFacade;
import com.example.demo.service.PetService;
import com.example.demo.service.RoomBookingService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/users")
public class UserController {

    @Autowired
    private AuthenticationFacade authenticationFacade;

    @Autowired
    private RoomBookingService roomBookingService;

    @Autowired
    private UserService userService;

    @Autowired
    private PetService petService;

    @GetMapping()
    public User getUser() {
        Authentication authentication = authenticationFacade.getAuthentication();
        return userService.findByEmail((String) authentication.getPrincipal());
    }

    @GetMapping("principal")
    public ResponseEntity<?> checkPrincipal() {
        Authentication authentication = authenticationFacade.getAuthentication();
        System.out.println("authen");
        System.out.println(authentication.toString());
        User user = userService.findByEmail((String) authentication.getPrincipal());
        System.out.println("prin");
        System.out.println(user.toString());
        return ResponseEntity.ok(user);
    }


    @GetMapping("/")
    public ResponseEntity<?> getAllUsers(Pageable pageable) {
        return ResponseEntity.ok(getUser());
    }


    //Xem info user
    @GetMapping("/{userId}")
    public User getAnUser(@PathVariable(name = "userId") Integer userId) {
        return userService.findById(userId);
    }

    // Xem list pet của user
    @GetMapping("/{userId}/pets")
    public List<Pet> getMyPets() {
        return getUser().getPets();
    }

    //Xem từng pet: xem được của bản thân những con isVisibale
    @GetMapping("/{userId}/pets/{petId}")
    public Pet getOnePet(@PathVariable(name = "userId") Integer userId, @PathVariable(name = "petId") Integer petId) {
        return petService.getOnePet(petId, getUser());
    }

    // xem list booking
    @GetMapping("/{userId}/room-bookings")
    public List<RoomBooking> getMyRoomBookings(@PathVariable(name = "userId") Integer userId) {
        return roomBookingService.findMyBookings(getUser());
    }


    @GetMapping("/{userId}/room-bookings/{roomBookingId}")
    public RoomBooking getOneRoomBooking(@PathVariable(name = "userId") Integer userId, @PathVariable(name = "roomBookingId") Integer roomBookingId) {
        return roomBookingService.findByIdWithUser(roomBookingId, getUser());
    }

    @PostMapping("/create")
    public User createUser(@RequestBody CreateUserRequest request){
        return userService.createUser(request);
    }



}
