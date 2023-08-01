package com.example.demo.service;

import com.example.demo.exception.ForbiddenException;
import com.example.demo.exception.NotFoundException;
import com.example.demo.model.entity.Pet;
import com.example.demo.model.entity.User;
import com.example.demo.model.enums.PaymentType;
import com.example.demo.model.enums.Sex;
import com.example.demo.model.request.CreateRoomBookingRequest;
import com.example.demo.model.roombooking.RoomBooking;
import com.example.demo.repository.RoomBookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoomBookingServiceImp implements RoomBookingService {
    @Autowired
    private RoomBookingRepository roomBookingRepository;

    public RoomBooking findById(Integer roomBookingId) {
        return roomBookingRepository.findById(roomBookingId).orElseThrow(() -> new NotFoundException("Không tìm thấy roomBooking với id " + roomBookingId));
    }

    @Override
    public List<RoomBooking> findMyBookings(User user) {
        List<Pet> pets = user.getPets();
        List<RoomBooking> roomBookings = new ArrayList<>();
        for (Pet pet : pets) {
            roomBookings.addAll(pet.getRoomBookings());
        }
        return roomBookings;
    }

    @Override
    public RoomBooking findByIdWithUser(Integer roomBookingId, User user) {
        RoomBooking roomBooking = this.findById(roomBookingId);
        if (findMyBookings(user).contains(roomBooking)) {
            return roomBooking;
        } else {
            throw new ForbiddenException("Bạn không thể truy cập room booking này");
        }
    }

    @Override
    public Page<RoomBooking> getAllRoomBookingsWithPage(Pageable pageable) {
        return roomBookingRepository.findAll(pageable);
    }

    @Override
    public RoomBooking makeARoomBooking(CreateRoomBookingRequest request, User user) {
        if (!user.getPets().contains(request.getPet())) {
            throw new NotFoundException("Không tìm thấy pet này trong danh sách pet của bạn");
        }

        RoomBooking roomBooking = RoomBooking.builder()
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .hotelRoomType(request.getHotelRoomType())
                .pet(request.getPet())
                .user(user)
                .build();

        if (request.getPaymentType().equalsIgnoreCase("CASH")) {
            roomBooking.setPaymentType(PaymentType.CASH);
        } else if (request.getPaymentType().equalsIgnoreCase("ZALO")) {
            roomBooking.setPaymentType(PaymentType.ZALO);
        } else {
            throw new NotFoundException("Payment not found");
        }

        roomBookingRepository.save(roomBooking);
        return roomBooking;
    }
}
