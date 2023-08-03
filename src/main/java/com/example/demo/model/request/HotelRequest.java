package com.example.demo.model.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class HotelRequest {

    @NotNull
    private String name;

    @NotNull
    private String city;

    @NotNull
    private String district;

    @NotNull
    private String address;

    @NotNull
    private String description;

    private Boolean status = true;

    private List<Integer> adminId;

    private List<Integer> roomTypeId;

    private List<Integer> totalRoom;

    private List<HotelRoomTypeRequest> hotelRoomTypeRequests = new ArrayList<>();

    private MultipartFile fileImage;

    public void convert (){
        if(this.roomTypeId.isEmpty()) {
            return;
        }

        for (int i=0; i < this.roomTypeId.size(); i++){
            HotelRoomTypeRequest hotelRoomTypeRequest = new HotelRoomTypeRequest();

            hotelRoomTypeRequest.setRoomTypeId(this.roomTypeId.get(i));
            hotelRoomTypeRequest.setTotalRoom(this.totalRoom.get(i));

            this.hotelRoomTypeRequests.add(hotelRoomTypeRequest);
        }
    };
}
