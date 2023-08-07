package com.example.demo.model.projection;

import com.example.demo.model.roombooking.RoomType;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class AvailableRoomTypeInfo {
    Integer hotelId;

    String hotelAddress;

    String hotelCity;
    String hotelDistrict;

    String hotelDescription;

    String hotelName;

    List<RoomTypeInfo> roomTypeInfoList;
}
