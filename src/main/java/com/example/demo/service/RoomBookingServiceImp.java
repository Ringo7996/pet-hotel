package com.example.demo.service;

import com.example.demo.exception.ForbiddenException;
import com.example.demo.exception.NotFoundException;
import com.example.demo.model.entity.Hotel;
import com.example.demo.model.entity.Pet;
import com.example.demo.model.entity.User;
import com.example.demo.model.enums.PaymentType;
import com.example.demo.model.enums.Status;
import com.example.demo.model.request.CreateRoomBookingRequest;
import com.example.demo.model.roombooking.HotelRoomType;
import com.example.demo.model.roombooking.RoomBooking;
import com.example.demo.repository.HotelRepository;
import com.example.demo.repository.HotelRoomTypeRepository;
import com.example.demo.repository.RoomBookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoomBookingServiceImp implements RoomBookingService {
    @Autowired
    private RoomBookingRepository roomBookingRepository;

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private HotelRoomTypeRepository hotelRoomTypeRepository;

    @Override
    public RoomBooking findById(Integer roomBookingId) {
        return roomBookingRepository.findById(roomBookingId).orElseThrow(() -> new NotFoundException("Không tìm thấy roomBooking với id " + roomBookingId));
    }

    @Override
    public void addTxnRefToRoomBooking(Integer roomBookingId, String vnp_txnRef) {
        RoomBooking roomBooking = findById(roomBookingId);
        roomBooking.setTxnRef(vnp_txnRef);
        roomBookingRepository.save(roomBooking);
    }

    @Override
    public RoomBooking findByTxnRef(String txnRef) {
        return roomBookingRepository.findByTxnRef(txnRef);
    }

    @Override
    public void changeStatusToConfirmed(String txnRef) {
        RoomBooking roomBooking = findByTxnRef(txnRef);
        roomBooking.setStatus(Status.CONFIRMED);
        roomBookingRepository.save(roomBooking);
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

    @Override
    public Page<RoomBooking> getAllRoomBookingsWithPage(Pageable pageable) {
        return roomBookingRepository.findAll(pageable);
    }

    @Override
    public Page<RoomBooking> getMyRoomBookingsWithPage(Pageable pageable, Integer userId) {
        List<Hotel> hotelByStaff = hotelRepository.findByStaffIdOrderById(userId);
        List<Integer> hotelIds = hotelByStaff.stream().map(hotel -> hotel.getId()).collect(Collectors.toList());

        List<HotelRoomType> hotelRoomTypes = hotelRoomTypeRepository.findByHotelIdList(hotelIds);
        List<Integer> hotelRoomTypeIds = hotelRoomTypes.stream().map(hrt -> hrt.getId()).collect(Collectors.toList());

        return roomBookingRepository.findByHotelRoomTypeIdsOrderById(hotelRoomTypeIds, pageable);
    }


}
