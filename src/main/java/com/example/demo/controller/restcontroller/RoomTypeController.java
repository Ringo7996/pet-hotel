package com.example.demo.controller.restcontroller;

import com.example.demo.model.roombooking.RoomType;
import com.example.demo.service.RoomTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/room-type")
public class RoomTypeController {

    @Autowired
    RoomTypeService roomTypeService;
    @GetMapping("/get-room-type-not-part-of-hotel/{id}")
    public ResponseEntity<?> getRoomTypeNotPartOfHotel(@PathVariable("id") Integer id){
       try {
           List<RoomType> roomTypeList = roomTypeService.findAllRoomsNotPartOfHotel(id);
           return ResponseEntity.ok(roomTypeList);
       } catch (Exception e){
           System.out.println(e.toString());
           return  ResponseEntity.badRequest().body(e.toString());
       }


    }
}
