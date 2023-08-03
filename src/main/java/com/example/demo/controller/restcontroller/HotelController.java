package com.example.demo.controller.restcontroller;

import com.example.demo.exception.BadRequestException;
import com.example.demo.model.entity.Hotel;
import com.example.demo.model.entity.User;
import com.example.demo.model.request.CreateHotelRequest;
import com.example.demo.model.request.CreateUserRequest;
import com.example.demo.model.request.HotelRequest;
import com.example.demo.service.HotelService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/v1/hotels")
public class HotelController {
    @Autowired
    private HotelService hotelService;

    @PostMapping("/create")
    public User createHotel(@RequestBody CreateHotelRequest request) {
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

    @PreAuthorize("hasAnyRole('ROOT_ADMIN')")
    @PostMapping("/updateHotel/{id}")
    public ResponseEntity<?> updateHotel(@PathVariable("id") Integer id,@Valid @ModelAttribute HotelRequest hotelRequest){
        try {
            hotelRequest.convert();
            hotelService.updateInfoHotel(hotelRequest,id);
            return ResponseEntity.ok("Update success");
        } catch (Exception e){
            System.out.println(e.toString());
            return ResponseEntity.badRequest().body(e.toString());
        }
    }

    @PostMapping("/delete-hotel/{id}")
    @PreAuthorize("hasAnyRole('ROOT_ADMIN')")
    public ResponseEntity<?> deleteHotel(@PathVariable("id") Integer id){

        try {
            hotelService.softDelete(id);
            return ResponseEntity.ok("Success");
        }catch (Exception e){
            System.out.println(e.toString());
            return ResponseEntity.badRequest().body("Hotel not Found");
        }

    }
    @PostMapping("/activity-hotel/{id}")
    @PreAuthorize("hasAnyRole('ROOT_ADMIN')")
    public ResponseEntity<?> activityHotel(@PathVariable("id") Integer id){

        try {
            hotelService.activityHotel(id);
            return ResponseEntity.ok("Success");
        }catch (Exception e){
            System.out.println(e.toString());
            return ResponseEntity.badRequest().body("Hotel not Found");
        }

    }

}
