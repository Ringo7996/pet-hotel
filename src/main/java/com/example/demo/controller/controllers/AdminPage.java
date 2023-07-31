package com.example.demo.controller.controllers;

import com.example.demo.model.entity.Hotel;
import com.example.demo.model.entity.Pet;
import com.example.demo.model.entity.User;
import com.example.demo.model.roombooking.RoomBooking;
import com.example.demo.model.roombooking.RoomType;
import com.example.demo.service.HotelService;
import com.example.demo.service.RoleService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasAnyRole('ROLE_ROOT_ADMIN', 'ROLE_ADMIN')")
public class AdminPage {
    @Autowired
    private UserService userService;

    @Autowired
    private HotelService hotelService;

    @Autowired
    private RoleService roleService;


    @GetMapping
    public String getAdminPage(){
        return "adm/index";
    }

    @GetMapping("/users/user-list")
    public String getUserPage(Model model, Pageable pageable){
        Page<User> userPage = userService.getAllUsersWithPage(pageable);
        model.addAttribute("page",userPage);
        model.addAttribute("currentPage",pageable.getPageNumber() + 1);
        return "adm/users/user-list";
    }

    @GetMapping("/users/user-create")
    public String getCreateUserPage(){
        return "adm/users/user-create";
    }

    @GetMapping("/users/{id}/detail")
    public String getUserDetailPage(Model model, @PathVariable(name = "id") Integer userId){
        User user = userService.findById(userId);
        System.out.println("user= " + user);

        List<Pet> pets = user.getPets();

        List<RoomBooking> roomBookings = user.getRoomBookings();

        boolean isRootAdmin = roleService.isRootAdmin(userId);

        boolean isAdmin = roleService.isAdmin(userId);

        model.addAttribute("user",user);
        model.addAttribute("petList",pets);
        model.addAttribute("roomBookingList",roomBookings);
        model.addAttribute("isRootAdmin",isRootAdmin);
        model.addAttribute("isAdmin",isAdmin);
        return "adm/users/user-detail";
    }




    @GetMapping("/hotels/hotel-list")
    public String getHotelPage(Model model, Pageable pageable){
        Page<Hotel> hotelPage = hotelService.getAllHotelsWithPage(pageable);
        model.addAttribute("page",hotelPage);
        model.addAttribute("currentPage",pageable.getPageNumber() +1);

        return "adm/hotels/hotel-list";
    }


    @GetMapping("/hotels/hotel-create")
    public String getCreateHotelPage(){
        return "adm/hotels/hotel-create";
    }


    @GetMapping("/hotels/{id}/detail")
    public String getHotelDetailPage(Model model, @PathVariable(name = "id") Integer hotelId){
        Hotel hotel = hotelService.findbyId(hotelId);
        List<RoomType> roomTypeList = hotelService.getRoomType(hotelId);
        List<User> staffs = hotel.getStaff();

        System.out.println("hotel" + hotel);
        System.out.println("staff" + hotel.getStaff());

        model.addAttribute("hotel",hotel);
        model.addAttribute("roomTypeList",roomTypeList);
        model.addAttribute("staffList",staffs);

        return "adm/hotels/hotel-detail";
    }




}
