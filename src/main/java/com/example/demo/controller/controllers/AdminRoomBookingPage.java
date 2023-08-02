package com.example.demo.controller.controllers;

import com.example.demo.model.entity.Hotel;
import com.example.demo.model.entity.User;
import com.example.demo.model.roombooking.HotelRoomType;
import com.example.demo.model.roombooking.RoomBooking;
import com.example.demo.model.roombooking.RoomType;
import com.example.demo.security.AuthenticationFacade;
import com.example.demo.service.RoleService;
import com.example.demo.service.RoomBookingService;
import com.example.demo.service.RoomTypeService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin/room-bookings")
public class AdminRoomBookingPage {
    @Autowired
    private AuthenticationFacade authenticationFacade;

    @Autowired
    private RoomBookingService roomBookingService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserService hotelService;

    @Autowired
    private RoomTypeService roomTypeService;

    @Autowired
    private RoleService roleService;

    @GetMapping("/room-booking-list")
    public String getRoomBookingList(Model model, Pageable pageable){
        Page<RoomBooking> roomBookingPage = roomBookingService.getAllRoomBookingsWithPage(pageable);

        model.addAllAttributes(Map.of(
                "page", roomBookingPage,
                "currentPage", pageable.getPageNumber()
        ));
        return "adm/room-bookings/room-booking-list";
    }

    @GetMapping("/my-room-bookings")
    public String getMyRoomBooking(Model model, Pageable pageable){
        Page<RoomBooking> hotelPage = roomBookingService.getMyRoomBookingsWithPage(pageable, getUser().getId());

        model.addAllAttributes(Map.of(
                "page", hotelPage,
                "currentPage", pageable.getPageNumber()
        ));

        return "adm/room-bookings/my-room-bookings";
    }


    @GetMapping("/room-booking-create")
    public String createRoomBooking(){
        return "adm/room-bookings/room-booking-create";
    }

    @GetMapping("/{id}/detail")
    public String getRoomBookingDetail(Model model, @PathVariable(name = "id") Integer roomBookingId){
        RoomBooking roomBooking = roomBookingService.findById(roomBookingId);
        Hotel hotel = roomBooking.getHotelRoomType().getHotel();
        RoomType roomType = roomBooking.getHotelRoomType().getRoomType();
        HotelRoomType hotelRoomType = roomBooking.getHotelRoomType();


        model.addAllAttributes(Map.of(
                "hotel",hotel,
                "roomType",roomType,
                "hotelRoomType",hotelRoomType,
                "roomBooking", roomBooking,
                "user", roomBooking.getUser(),
                "pet",roomBooking.getPet()
        ));



        return "adm/room-bookings/room-booking-detail";
    }

    @ModelAttribute("sharedModel")
    public Map<String, Object> getSharedModel() {
        Map<String, Object> sharedModel = new HashMap<>();
        sharedModel.put("isRootAdminRole", roleService.isRootAdmin(getUser().getId()));
        sharedModel.put("isAdminRole", roleService.isAdmin(getUser().getId()));
        return sharedModel;
    }

    public User getUser() {
        Authentication authentication = authenticationFacade.getAuthentication();
        return userService.findByEmail((String) authentication.getPrincipal());
    }


}
