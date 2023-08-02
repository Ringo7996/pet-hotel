package com.example.demo.controller.controllers;

import com.example.demo.model.entity.Hotel;
import com.example.demo.model.entity.User;
import com.example.demo.model.roombooking.HotelRoomType;
import com.example.demo.model.roombooking.RoomType;
import com.example.demo.security.AuthenticationFacade;
import com.example.demo.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("/admin/room-types")
//@PreAuthorize("hasAnyRole('ROLE_ROOT_ADMIN', 'ROLE_ADMIN')")
public class AdminRoomTypePage {
    @Autowired
    private AuthenticationFacade authenticationFacade;

    @Autowired
    private UserService userService;

    @Autowired
    private RoomTypeService roomTypeService;

    @Autowired
    private HotelRoomTypeService hotelRoomTypeService;


    @Autowired
    private HotelService hotelService;

    @Autowired
    private RoleService roleService;


    @GetMapping("/room-type-list")
    public String getRoomTypeList(Model model) {
        List<RoomType> roomTypes = roomTypeService.getAllRoomType();
        model.addAttribute("roomTypeList",roomTypes);
        return "adm/room-types/room-type-list";
    }


    @GetMapping("/room-type-create")
    public String createRoomType(Model model) {
        return "adm/room-types/room-type-create";
    }

    @GetMapping("/{id}/detail")
    public String getRoomTypeDetail(Model model, @PathVariable(name = "id") Integer roomTypeId) {
        RoomType roomType = roomTypeService.findById(roomTypeId);
        List<HotelRoomType> hotelRoomTypes = hotelRoomTypeService.findByRoomType_Id(roomTypeId);

        model.addAllAttributes(Map.of(
                "roomType", roomType,
                "hotelRoomTypeList", hotelRoomTypes
        ));

        return "adm/room-types/room-type-detail";
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
