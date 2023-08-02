package com.example.demo.controller.restcontroller;

import com.example.demo.exception.BadRequestException;
import com.example.demo.model.entity.Hotel;
import com.example.demo.model.entity.User;
import com.example.demo.model.request.CreateHotelRequest;
import com.example.demo.model.request.CreateUserRequest;
import com.example.demo.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

@RestController
@RequestMapping("api/v1/hotels")
public class HotelController {
    @Autowired
    private HotelService hotelService;

    @PostMapping("/create")
    public User createUser(@RequestBody CreateHotelRequest request) {
        return hotelService.createHotel(request);
    }

    @GetMapping("city")
    public List<String> getAllCity() {
        return hotelService.getAllCity();
    }

    @GetMapping("district")
    public List<String> getAllDistrictByCity(@RequestParam String city) {
        return hotelService.getAllDistrictByCity(city);
    }


    @GetMapping("available-hotel-by-date-range")
    public List<Hotel> getAvailableHotelByDateRange(@RequestParam String district,
                                                    @RequestParam(name = "start") String start,
                                                    @RequestParam(name = "end") String end) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate startDay = LocalDate.parse(start, formatter);
            LocalDate endDay = LocalDate.parse(end, formatter);
            return hotelService.getAvailableHotelByDateRange(district, startDay, endDay);
        } catch (DateTimeParseException e) {
            throw new BadRequestException("Date is not valid");
        } catch (Exception e){
            throw new BadRequestException(e.toString());
        }
    }
}
