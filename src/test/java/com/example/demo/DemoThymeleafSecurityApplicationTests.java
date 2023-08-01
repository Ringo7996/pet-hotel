package com.example.demo;

import com.example.demo.model.entity.*;
import com.example.demo.model.enums.Sex;
import com.example.demo.model.roombooking.HotelRoomType;
import com.example.demo.model.roombooking.RoomBooking;
import com.example.demo.model.roombooking.RoomType;
import com.example.demo.repository.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest
class DemoThymeleafSecurityApplicationTests {
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PetRepository petRepository;
    @Autowired
    private HotelRepository hotelRepository;
    @Autowired
    private PaymentTypeRepository paymentTypeRepository;
    @Autowired
    private RoomTypeRepository roomTypeRepository;
    @Autowired
    private RoomBookingRepository roomBookingRepository;
    @Autowired
    private HotelRoomTypeRepository hotelRoomTypeRepository;
    @Autowired
    private PasswordEncoder encoder;


    @Test
    void save_role() {
        List<Role> roles = List.of(
                Role.builder().name("USER").build(),
                Role.builder().name("ROOT_ADMIN").build(),
                Role.builder().name("ADMIN").build()
        );
        roleRepository.saveAll(roles);
    }

    @Test
    void save_pet() {
        List<Pet> pet = List.of(
                Pet.builder().name("Xám").breed("ta").type("mèo").color("xám").sex(Sex.MALE).build(),
                Pet.builder().name("Đen").breed("mèo ta").type("mèo").color("đen").sex(Sex.FEMALE).build(),
                Pet.builder().name("Bò Sữa").breed("mèo tây").type("chó").color("vàng").sex(Sex.MALE).build(),
                Pet.builder().name("Giẻ Lau").breed("mèo tây").type("mèo").color("đen").sex(Sex.MALE).build(),
                Pet.builder().name("Vàng").breed("mèo tây").type("chó").color("vàng").sex(Sex.MALE).build(),
                Pet.builder().name("Mẹ Xề").breed("mèo tây").type("mèo").color("tam thể").sex(Sex.FEMALE).build(),
                Pet.builder().name("Bin").breed("mèo con").type("chó").color("tam thể").sex(Sex.FEMALE).build(),
                Pet.builder().name("Bo").breed("mèo ai cập").type("mèo").color("tam thể").sex(Sex.FEMALE).build()
        );
        petRepository.saveAll(pet);
    }


    @Test
    void save_hotel() {
        List<Hotel> hotels = List.of(
                Hotel.builder().name("Hotel 1").address("Address I").city("Hà Nội").district("Đống đa").description("Description I").build(),
                Hotel.builder().name("Hotel 2").address("Address K").city("Hà Nội").district("Thanh xuân").description("Description K").build(),
                Hotel.builder().name("Hotel 3").address("Address L").city("Hà Nội").district("Đống đa").description("Description L").build(),
                Hotel.builder().name("Hotel 4").address("Address M").city("Hà Nội").district("Thanh xuân").description("Description M").build(),
                Hotel.builder().name("Hotel 5").address("Address E").city("Hà Nội").district("Đống đa").description("Description E").build(),
                Hotel.builder().name("Hotel 6").address("Address F").city("HCM").district("Quận 1").description("Description F").build(),
                Hotel.builder().name("Hotel 7").address("Address G").city("HCM").district("Quận 10").description("Description G").build(),
                Hotel.builder().name("Hotel 8").address("Address H").city("Hà Nội").district("Thanh Xuân").description("Description H").build(),
                Hotel.builder().name("Hotel 9").address("Address H").city("HCM").district("Quận 10").description("Description H").build(),
                Hotel.builder().name("Hotel 10").address("Address H").city("Hà Nội").district("Đống Đa").description("Description H").build(),
                Hotel.builder().name("Hotel 11").address("Address H").city("HCM").district("Quận 1").description("Description H").build(),
                Hotel.builder().name("Hotel 12").address("Address H").city("Hà Nội").district("Đống Đa").description("Description H").build()
        );
        hotelRepository.saveAll(hotels);
    }

    @Test
    void save_user() {
        Role rootAdmin = roleRepository.findByName("ROOT_ADMIN").orElse(null);
        Role hotelAdmin = roleRepository.findByName("ADMIN").orElse(null);
        Role user = roleRepository.findByName("USER").orElse(null);

        Hotel hotel1 = hotelRepository.findById(1).orElse(null);
        Hotel hotel2 = hotelRepository.findById(2).orElse(null);
        Hotel hotel3 = hotelRepository.findById(3).orElse(null);

        Pet pet1 = petRepository.findById(1).orElse(null);
        Pet pet2 = petRepository.findById(2).orElse(null);
        Pet pet3 = petRepository.findById(3).orElse(null);
        Pet pet4 = petRepository.findById(4).orElse(null);
        Pet pet5 = petRepository.findById(5).orElse(null);
        Pet pet6 = petRepository.findById(6).orElse(null);
        Pet pet7 = petRepository.findById(7).orElse(null);
        Pet pet8 = petRepository.findById(8).orElse(null);

        User user1 = User.builder().name("Nguyễn Văn A").phone("0123456789").roles(List.of(rootAdmin))
                .email("rootadmin@gmail.com").password(encoder.encode("123"))
                .pets(List.of(pet1, pet2)).build();
        User user2 = User.builder().name("Nguyễn Văn B").phone("0123456789").roles(List.of(hotelAdmin))
                .email("admin1@gmail.com").password(encoder.encode("123"))
                .myHotels(List.of(hotel1,hotel2))
                .pets(List.of(pet3, pet4)).build();
        User user3 = User.builder().name("Nguyễn Văn C").phone("0123456789").roles(List.of(hotelAdmin,user))
                .email("admin2@gmail.com").password(encoder.encode("123"))
                .myHotels(List.of(hotel2,hotel3,hotel1))
                .pets(List.of(pet5,pet6)).build();
        User user4 = User.builder().name("Nguyễn Văn D").phone("0123456789").roles(List.of(user))
                .email("user@gmail.com").password(encoder.encode("123"))
                .pets(List.of(pet7,pet8)).build();

        userRepository.saveAll(List.of(user1,user2,user3,user4));

        pet1.setUser(user1);
        pet2.setUser(user1);
        pet3.setUser(user2);
        pet4.setUser(user2);
        pet5.setUser(user3);
        pet6.setUser(user3);
        List<Pet> pets = List.of(pet1,pet2,pet3,pet4,pet5,pet6);
        petRepository.saveAll(pets);
    }



    @Test
    void save_payment_type(){
        List<PaymentType> paymentTypes = List.of(
                PaymentType.builder().name("Thanh toán trực tiếp").build(),
                PaymentType.builder().name("Thanh toán zalo pay").build()
        );
        paymentTypeRepository.saveAll(paymentTypes);
    }

    @Test
    void save_room_type(){
        List<RoomType> roomTypes = List.of(
                RoomType.builder().name("Phòng vip").price(250000.0).description("Phòng vip xịn xò").build(),
                RoomType.builder().name("Phòng thường").price(250000.0).description("Phòng bình thường thôi").build(),
                RoomType.builder().name("Phòng tiết kiệm").price(250000.0).description("Phòng tiết kiệm ở tạm").build()
        );
        roomTypeRepository.saveAll(roomTypes);
    }

    @Test
    void save_hotel_room_type(){
        Hotel hotel1 = hotelRepository.findById(1).orElse(null);
        Hotel hotel2 = hotelRepository.findById(2).orElse(null);
        Hotel hotel3 = hotelRepository.findById(3).orElse(null);
        Hotel hotel4 = hotelRepository.findById(4).orElse(null);
        RoomType roomType1 = roomTypeRepository.findById(1).orElse(null);
        RoomType roomType2 = roomTypeRepository.findById(2).orElse(null);
        RoomType roomType3 = roomTypeRepository.findById(3).orElse(null);


        List<HotelRoomType> hotelRoomTypes = List.of(
                HotelRoomType.builder().hotel(hotel1).roomType(roomType1).totalRoomNumber(5).build(),
                HotelRoomType.builder().hotel(hotel1).roomType(roomType2).totalRoomNumber(10).build(),
                HotelRoomType.builder().hotel(hotel2).roomType(roomType1).totalRoomNumber(3).build(),
                HotelRoomType.builder().hotel(hotel2).roomType(roomType2).totalRoomNumber(4).build(),
                HotelRoomType.builder().hotel(hotel2).roomType(roomType3).totalRoomNumber(5).build(),
                HotelRoomType.builder().hotel(hotel3).roomType(roomType1).totalRoomNumber(2).build(),
                HotelRoomType.builder().hotel(hotel3).roomType(roomType2).totalRoomNumber(4).build(),
                HotelRoomType.builder().hotel(hotel3).roomType(roomType3).totalRoomNumber(3).build(),
                HotelRoomType.builder().hotel(hotel4).roomType(roomType2).totalRoomNumber(8).build(),
                HotelRoomType.builder().hotel(hotel4).roomType(roomType3).totalRoomNumber(10).build()
        );
        hotelRoomTypeRepository.saveAll(hotelRoomTypes);
    }


    @Test
    void save_room_booking(){
        Pet pet1 = petRepository.findById(1).orElse(null);
        Pet pet2 = petRepository.findById(2).orElse(null);
        Pet pet3 = petRepository.findById(3).orElse(null);
        Pet pet4 = petRepository.findById(4).orElse(null);
        Pet pet5 = petRepository.findById(5).orElse(null);
        Pet pet6 = petRepository.findById(6).orElse(null);

        HotelRoomType hotelRoomType1 = hotelRoomTypeRepository.findById(1).orElse(null);
        HotelRoomType hotelRoomType2 = hotelRoomTypeRepository.findById(2).orElse(null);
        HotelRoomType hotelRoomType3 = hotelRoomTypeRepository.findById(3).orElse(null);

        PaymentType paymentType1 = paymentTypeRepository.findById(1).orElse(null);
        PaymentType paymentType2 = paymentTypeRepository.findById(1).orElse(null);

        List<RoomBooking> roomBookings = List.of(
                RoomBooking.builder().pet(pet1).startDate(LocalDate.now()).endDate(LocalDate.now().plusDays(3)).hotelRoomType(hotelRoomType3).paymentType(paymentType1).build(),
                RoomBooking.builder().pet(pet2).startDate(LocalDate.now()).endDate(LocalDate.now().plusDays(4)).hotelRoomType(hotelRoomType1).paymentType(paymentType2).build(),
                RoomBooking.builder().pet(pet3).startDate(LocalDate.now()).endDate(LocalDate.now().plusDays(1)).hotelRoomType(hotelRoomType2).paymentType(paymentType1).build(),
                RoomBooking.builder().pet(pet1).startDate(LocalDate.now().plusDays(5)).endDate(LocalDate.now().plusDays(2)).hotelRoomType(hotelRoomType2).paymentType(paymentType1).build()
        );

        roomBookingRepository.saveAll(roomBookings);
    }


}
