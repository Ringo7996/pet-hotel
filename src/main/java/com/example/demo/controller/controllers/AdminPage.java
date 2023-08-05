package com.example.demo.controller.controllers;

import com.example.demo.model.entity.Hotel;
import com.example.demo.model.entity.Pet;
import com.example.demo.model.entity.User;
import com.example.demo.model.roombooking.HotelRoomType;
import com.example.demo.model.roombooking.RoomBooking;
import com.example.demo.model.roombooking.RoomType;
import com.example.demo.security.AuthenticationFacade;
import com.example.demo.service.HotelRoomTypeService;
import com.example.demo.service.HotelService;
import com.example.demo.service.RoleService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
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
@RequestMapping("/admin")
@PreAuthorize("hasAnyRole('ROLE_ROOT_ADMIN', 'ROLE_ADMIN')")
public class AdminPage {
    @Autowired
    private AuthenticationFacade authenticationFacade;

    @Autowired
    private UserService userService;

    @Autowired
    private HotelService hotelService;

    @Autowired
    private HotelRoomTypeService hotelRoomTypeService;

    @Autowired
    private RoleService roleService;

    @ModelAttribute("sharedModel")
    public Map<String, Object> getSharedModel() {
        Map<String, Object> sharedModel = new HashMap<>();
        sharedModel.put("isRootAdminRole", roleService.isRootAdmin(getUser().getId()));
        sharedModel.put("isAdminRole", roleService.isAdmin(getUser().getId()));
        return sharedModel;
    }

    @GetMapping
    public String getAdminPage() {
        return "adm/index";
    }

    @GetMapping("/users/user-list")
    public String getUserPage(Model model, Pageable pageable) {
        Page<User> userPage = userService.getAllUsersWithPage(pageable);

        model.addAllAttributes(Map.of(
                "page", userPage,
                "currentPage", pageable.getPageNumber()
        ));

        return "adm/users/user-list";
    }

    @GetMapping("/users/user-create")
    public String getCreateUserPage(Model model) {
        return "adm/users/user-create";
    }

    @GetMapping("/users/{id}/detail")
    public String getUserDetailPage(Model model, @PathVariable(name = "id") Integer userId) {
        User user = userService.findById(userId);
        List<Pet> pets = user.getPets();
        List<Hotel> hotels = user.getMyHotels();
        List<RoomBooking> roomBookings = user.getRoomBookings();
        boolean isRootAdmin = roleService.isRootAdmin(userId);
        boolean isAdmin = roleService.isAdmin(userId);

        model.addAllAttributes(Map.of(
                "user", user,
                "petList", pets,
                "hotelList", hotels,
                "roomBookingList", roomBookings,
                "isRootAdmin", isRootAdmin,
                "isAdmin", isAdmin
        ));

        return "adm/users/user-detail";
    }


    @GetMapping("/hotels/hotel-list")
    public String getHotelPage(Model model, Pageable pageable) {
        Page<Hotel> hotelPage = hotelService.getHotelsActivityWithPage(true,pageable);

        model.addAllAttributes(Map.of(
                "page", hotelPage,
                "currentPage", pageable.getPageNumber()
        ));

        return "adm/hotels/hotel-list";
    }
    @GetMapping("/hotels/hotel-list-not-activity")
    public String getHotelPageNotActivity(Model model, Pageable pageable) {
        Page<Hotel> hotelPage = hotelService.getHotelsActivityWithPage(false,pageable);

        model.addAllAttributes(Map.of(
                "page", hotelPage,
                "currentPage", pageable.getPageNumber()
        ));

        return "adm/hotels/hotel-list-not-activity";
    }

    @GetMapping("/hotels/my-hotels")
    public String getMyHotels(Model model, Pageable pageable) {
        Page<Hotel> hotelPage = hotelService.getMyHotelsWithPage(pageable, getUser().getId());

        model.addAllAttributes(Map.of(
                "page", hotelPage,
                "currentPage", pageable.getPageNumber()
        ));

        return "adm/hotels/my-hotels";
    }


    @GetMapping("/hotels/hotel-create")
    public String getCreateHotelPage(Model model) {
        return "adm/hotels/hotel-create";
    }


    @GetMapping("/hotels/{id}/detail")
    public String getHotelDetailPage(Model model, @PathVariable(name = "id") Integer hotelId) {
        Hotel hotel = hotelService.findById(hotelId);
        List<HotelRoomType> hotelRoomTypes = hotelRoomTypeService.findByHotel_Id(hotelId);
        List<User> staffs = hotel.getStaff();
        staffs.forEach(e -> System.out.println(e.getId()));
        model.addAllAttributes(Map.of(
                "hotel", hotel,
                "hotelRoomTypeList", hotelRoomTypes,
                "staffList", staffs
        ));

        return "adm/hotels/hotel-detail";
    }


    public User getUser() {
        Authentication authentication = authenticationFacade.getAuthentication();
        return userService.findByEmail((String) authentication.getPrincipal());
    }


}
