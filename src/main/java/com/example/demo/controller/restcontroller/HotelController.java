package com.example.demo.controller.restcontroller;

import com.example.demo.model.entity.User;
import com.example.demo.model.request.CreateHotelRequest;
import com.example.demo.model.request.CreateUserRequest;
import com.example.demo.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/hotels")
public class HotelController {
    @Autowired
    private HotelService hotelService;

    @PostMapping("/create")
    public User createUser(@RequestBody CreateHotelRequest request){
        return hotelService.createHotel(request);
    }

}
