package com.example.demo.service;

import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.NotFoundException;
import com.example.demo.model.entity.Hotel;
import com.example.demo.model.entity.Image;
import com.example.demo.model.entity.User;
import com.example.demo.model.request.CreateHotelRequest;
import com.example.demo.model.request.HotelRequest;
import com.example.demo.model.request.HotelRoomTypeRequest;
import com.example.demo.model.roombooking.HotelRoomType;
import com.example.demo.model.roombooking.RoomType;
import com.example.demo.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class HotelServiceImp implements HotelService {
    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private HotelRoomTypeRepository hotelRoomTypeRepository;

    @Autowired
    private HotelRoomTypeService hotelRoomTypeService;

    @Autowired
    private RoomBookingRepository roomBookingRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ImageService imageService;
    @Autowired
    private RoomTypeService roomTypeService;

    @Autowired
    private RoomTypeRepository roomTypeRepository;

    @Autowired
    private ImageRepository imageRepository;
    @Override
    public void updateInfoHotel(HotelRequest hotelRequest, Integer id) {
        Optional<Hotel> opHotel = hotelRepository.findById(id);
        if(opHotel.isEmpty()) throw  new NotFoundException("Not found");

        Hotel  hotel = opHotel.get();

        hotel.setName(hotelRequest.getName());
        hotel.setCity(hotelRequest.getCity());
        hotel.setDistrict(hotelRequest.getDistrict());
        hotel.setAddress(hotelRequest.getAddress());
        hotel.setStatus(hotelRequest.getStatus());

        if(hotelRequest.getFileImage() != null){
            imageService.uploadByHotel(id,hotelRequest.getFileImage());
        }

        List<Integer> adminIds = hotelRequest.getAdminId();

        List<User> staffs = new ArrayList<>();

        List<User> users = hotel.getStaff();
        for (User user : users) {
            user.getMyHotels().remove(hotel);
            userRepository.save(user);
        }

        if(!adminIds.isEmpty()) {
            for (Integer e : adminIds) {
                User admin = userService.findById(e);
                admin.getMyHotels().add(hotel);
                userRepository.save(admin);
                staffs.add(admin);
            }
        }

        hotel.setStaff(staffs);

        List<HotelRoomTypeRequest> hotelRoomTypeRequestList = hotelRequest.getHotelRoomTypeRequests();
        hotelRoomTypeRepository.deleteByHotelId(id);
        if(!hotelRoomTypeRequestList.isEmpty()){
            for (HotelRoomTypeRequest e: hotelRoomTypeRequestList) {

                RoomType roomType = roomTypeService.findById(e.getRoomTypeId());
                HotelRoomType hotelRoomType = new HotelRoomType();
                hotelRoomType.setHotel(hotel);
                hotelRoomType.setRoomType(roomType);
                hotelRoomType.setTotalRoomNumber(e.getTotalRoom());
                hotelRoomTypeRepository.save(hotelRoomType);
            }
        }
        hotelRepository.save(hotel);
    }

    @Override
    public Page<Hotel> getAllHotelsWithPage(Pageable pageable) {
        return hotelRepository.findAll(pageable);
    }

    @Override
    public Hotel findById(Integer hotelId) {
        return hotelRepository.findById(hotelId).orElseThrow(() -> new NotFoundException("Hotel with id " + hotelId + " is not found"));
    }

    @Override
    public List<RoomType> getRoomType(Integer hotelId) {
        List<RoomType> roomTypeList = new ArrayList<>();
        List<HotelRoomType> hotelRoomTypes = hotelRoomTypeRepository.findByHotel_Id(hotelId);
        for (HotelRoomType hotelRoomType : hotelRoomTypes) {
            roomTypeList.add(hotelRoomType.getRoomType());
        }
        return roomTypeList;
    }

    @Override
    public User createHotel(CreateHotelRequest request) {
        return null;
    }

    @Override
    public Page<Hotel> getMyHotelsWithPage(Pageable pageable, Integer userId) {
        return hotelRepository.findByStaffIdOrderById(userId, pageable);
    }

    @Override
    public List<String> getAllCity() {
        return hotelRepository.getAllCity();
    }

    @Override
    public List<String> getAllDistrictByCity(String city) {
        return hotelRepository.getAllDistrictByCity(city);
    }


    @Override
    public List<Hotel> getAvailableHotelByDateRange(String district, LocalDate startDay, LocalDate endDay) {

        List<Hotel> hotels = hotelRepository.getAllHotelByDistrict(district);

        List<Integer> hotelIds = hotels.stream().map(hotel -> hotel.getId()).collect(Collectors.toList());
        System.out.println("lisst hotel ID= " + hotelIds);

        // lấy các hotel room type theo các hotel truyền vào (đã lọc theo thành phố/quận)
        List<HotelRoomType> hotelRoomTypesByHotels = hotelRoomTypeRepository.findByHotelIdList(hotelIds);
        System.out.println("hotelRoomTypesByHotels= " + hotelRoomTypesByHotels.size());

        List<HotelRoomType> availableHotelRoomTypes = new ArrayList<>();

        // duyệt từng hotel room type, xem trong khoảng thời gian đó đã bị đặt mấy phòng -> nếu còn phòng thì add vào available list
        for (HotelRoomType hotelRoomType : hotelRoomTypesByHotels) {
           int bookedRoomNum = roomBookingRepository.countByDateRange(hotelRoomType.getId(),startDay,endDay);
            System.out.println("hotelRoomTypeid= " + hotelRoomType.getId() + " bookedRoomNum" + bookedRoomNum);
           if (hotelRoomType.getTotalRoomNumber() > bookedRoomNum){
               availableHotelRoomTypes.add(hotelRoomType);
           }
        }

        System.out.println(availableHotelRoomTypes);
        List<Integer> availHotelRoomTypeIds = availableHotelRoomTypes.stream().map(hrt -> hrt.getId()).collect(Collectors.toList());
        List<Hotel> availableHotels = hotelRepository.findByHotelRoomTypeIdList(availHotelRoomTypeIds);

        return availableHotels;
    }

    @Override
    public void createHotel(HotelRequest hotelRequest) {

        List<Hotel> hotelsSystem = hotelRepository.findAllByName(hotelRequest.getName());
        if(!hotelsSystem.isEmpty()) throw new RuntimeException("Hotel already exists");

        Hotel newHotel = new Hotel();

        newHotel.setName(hotelRequest.getName());
        newHotel.setCity(hotelRequest.getCity());
        newHotel.setDescription(hotelRequest.getDescription());
        newHotel.setDistrict(hotelRequest.getDistrict());
        newHotel.setAddress(hotelRequest.getAddress());

        if(hotelRequest.getFileImage() != null){
            try {
                MultipartFile file = hotelRequest.getFileImage();
                imageService.validateFile(file);
                Image image2upload = Image.builder()
                        .type(file.getContentType())
                        .data(file.getBytes())
                        .hotel(newHotel)
                        .build();
                newHotel.setImage(image2upload);
                imageRepository.save(image2upload);
            } catch (Exception e){
                throw new RuntimeException(e.toString());
            }
        }
        hotelRepository.save(newHotel);
    }


}
