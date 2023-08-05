package com.example.demo.controller.restcontroller;


import com.example.demo.model.entity.Pet;
import com.example.demo.model.entity.User;
import com.example.demo.model.projection.UserListInfo;
import com.example.demo.model.request.CreateUserRequest;
import com.example.demo.model.roombooking.RoomBooking;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.AuthenticationFacade;
import com.example.demo.service.PetService;
import com.example.demo.service.RoomBookingService;
import com.example.demo.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
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

    @Autowired
    UserRepository userRepository;

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

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/delete-user/{id}")
    @PreAuthorize("hasAnyRole('ROOT_ADMIN')")
    public ResponseEntity<?> deleteUser(@PathVariable("id") Integer id,
                                        HttpSession session,
                                        HttpServletRequest request,
                                        HttpServletResponse response){
        try {
            userService.softDelete(id);
            String email =(String) session.getAttribute("MY_SESSION");
            User user = userService.findByEmail(email);
            if(user.getId().equals(id)){
                Authentication auth = SecurityContextHolder.getContext().getAuthentication();
                if (auth != null) {
                    new SecurityContextLogoutHandler().logout(request, response, auth);

                }
                return ResponseEntity.ok("/logout");
            }
            return ResponseEntity.ok("1223");
        }catch (Exception e){
            System.out.println(e.toString());
            return ResponseEntity.badRequest().body(e);
        }

    }

    @PostMapping("/activity-user/{id}")
    @PreAuthorize("hasAnyRole('ROOT_ADMIN')")
    public ResponseEntity<?> activityUser(@PathVariable("id") Integer id){
        try {
            userService.activityUser(id);
            return ResponseEntity.ok("Success");
        }catch (Exception e){
            System.out.println(e.toString());
            return ResponseEntity.badRequest().body(e);
        }

    }

    @GetMapping("/get-user")
//    @PreAuthorize("hasAnyRole('ROOT_ADMIN')")
    public ResponseEntity<?> getActivityUser(@Param("type") String type,
                                             @Param("value") String value,
                                             Pageable pageable){
        try {
            Page<UserListInfo> users = null;
            if(type.equalsIgnoreCase("not-activity"))
                users = userService.getUsersByStatusWithPage(false,pageable,value);
            else if(type.equalsIgnoreCase("activity")){
                users = userService.getUsersByStatusWithPage(true,pageable,value);
            }else
                users = userRepository.findByKeywordIgnoreCase(value,pageable);
            return ResponseEntity.ok(users);
        }catch (Exception e){
            System.out.println(e.toString());
            return ResponseEntity.badRequest().body(e);
        }
    }

}
