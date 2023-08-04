package com.example.demo;

import com.example.demo.model.entity.*;
import com.example.demo.model.enums.PaymentType;
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
    private RoomTypeRepository roomTypeRepository;
    @Autowired
    private RoomBookingRepository roomBookingRepository;
    @Autowired
    private HotelRoomTypeRepository hotelRoomTypeRepository;
    @Autowired
    private PasswordEncoder encoder;



    @Test
    void saveData(){
        save_role();
        save_pet();
        save_hotel();
        save_user();
        save_room_type();
        save_hotel_room_type();
        save_room_booking();
    }



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
                Pet.builder().name("Xám").breed("ta").type("mèo").color("xám").status(true).sex(Sex.MALE).build(),
                Pet.builder().name("Đen").breed("mèo ta").type("mèo").color("đen").status(true).sex(Sex.FEMALE).build(),
                Pet.builder().name("Bò Sữa").breed("mèo tây").type("chó").color("vàng").status(true).sex(Sex.MALE).build(),
                Pet.builder().name("Giẻ Lau").breed("mèo tây").type("mèo").color("đen").status(true).sex(Sex.MALE).build(),
                Pet.builder().name("Vàng").breed("mèo tây").type("chó").color("vàng").status(true).sex(Sex.MALE).build(),
                Pet.builder().name("Mẹ Xề").breed("mèo tây").type("mèo").color("tam thể").status(true).sex(Sex.FEMALE).build(),
                Pet.builder().name("Bin").breed("mèo con").type("chó").color("tam thể").status(true).sex(Sex.FEMALE).build(),
                Pet.builder().name("Bo").breed("mèo ai cập").type("mèo").color("tam thể").status(true).sex(Sex.FEMALE).build()
        );
        petRepository.saveAll(pet);
    }

    @Test
    void save_hotel() {
        List<Hotel> hotels = List.of(
                Hotel.builder().name("Hotel I").address("Address I").city("Hà Nội").district("Đống đa").description("Description I").build(),
                Hotel.builder().name("Hotel K").address("Address K").city("Hà Nội").district("Thanh xuân").description("Description K").build(),
                Hotel.builder().name("Hotel L").address("Address L").city("Hà Nội").district("Đống đa").description("Description L").build(),
                Hotel.builder().name("Hotel M").address("Address M").city("Hà Nội").district("Thanh xuân").description("Description M").build(),
                Hotel.builder().name("Hotel E").address("Address E").city("Hà Nội").district("Đống đa").description("Description E").build(),
                Hotel.builder().name("Hotel F").address("Address F").city("HCM").district("Quận 1").description("Description F").build(),
                Hotel.builder().name("Hotel G").address("Address G").city("HCM").district("Quận 10").description("Description G").build(),
                Hotel.builder().name("Hotel H").address("Address H").city("HCM").district("Quận 10").description("Description H").build()
        );
        hotelRepository.saveAll(hotels);
    }


    @Test
    //tắt cascade = CascadeType.ALL ở role và user trc khi chạy
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
                .email("user@gmail.com").password(encoder.encode("123")).build();

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
    void save_room_type(){
        List<RoomType> roomTypes = List.of(
                RoomType.builder().name("Phòng vip").price(250000.0).description("Phòng vip xịn xò").build(),
                RoomType.builder().name("Phòng thường").price(250000.0).description("Phòng bình thường thôi").build(),
                RoomType.builder().name("Phòng tiết kiệm").price(250000.0).description("Phòng tiết kiệm ở tạm").build()
        );
        roomTypeRepository.saveAll(roomTypes);
    }

    @Test
    // tắt       cascade = CascadeType.ALL ở hotel room type trc khi chạy
    void save_hotel_room_type(){
        Hotel hotel1 = hotelRepository.findById(1).orElse(null);
        Hotel hotel2 = hotelRepository.findById(2).orElse(null);
        Hotel hotel3 = hotelRepository.findById(3).orElse(null);
        Hotel hotel4 = hotelRepository.findById(4).orElse(null);
        RoomType roomType1 = roomTypeRepository.findById(1).orElse(null);
        RoomType roomType2 = roomTypeRepository.findById(2).orElse(null);
        RoomType roomType3 = roomTypeRepository.findById(3).orElse(null);

        System.out.println(hotel1);


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
        HotelRoomType hotelRoomType4 = hotelRoomTypeRepository.findById(4).orElse(null);
        HotelRoomType hotelRoomType5 = hotelRoomTypeRepository.findById(5).orElse(null);
        HotelRoomType hotelRoomType6 = hotelRoomTypeRepository.findById(6).orElse(null);
        HotelRoomType hotelRoomType7 = hotelRoomTypeRepository.findById(7).orElse(null);
        HotelRoomType hotelRoomType8 = hotelRoomTypeRepository.findById(8).orElse(null);
        HotelRoomType hotelRoomType9 = hotelRoomTypeRepository.findById(9).orElse(null);


        List<RoomBooking> roomBookings = List.of(
                RoomBooking.builder().pet(pet4).startDate(LocalDate.now().plusDays(4)).endDate(LocalDate.now().plusDays(3)).hotelRoomType(hotelRoomType5).paymentType(com.example.demo.model.enums.PaymentType.ZALO).build(),
                RoomBooking.builder().pet(pet5).startDate(LocalDate.now().plusDays(2)).endDate(LocalDate.now().plusDays(2)).hotelRoomType(hotelRoomType4).paymentType(com.example.demo.model.enums.PaymentType.CASH).build(),
                RoomBooking.builder().pet(pet6).startDate(LocalDate.now().plusDays(1)).endDate(LocalDate.now().plusDays(1)).hotelRoomType(hotelRoomType8).paymentType(com.example.demo.model.enums.PaymentType.ZALO).build(),
                RoomBooking.builder().pet(pet1).startDate(LocalDate.now().plusDays(3)).endDate(LocalDate.now().plusDays(2)).hotelRoomType(hotelRoomType6).paymentType(com.example.demo.model.enums.PaymentType.ZALO).build(),
                RoomBooking.builder().pet(pet1).startDate(LocalDate.now()).endDate(LocalDate.now().plusDays(3)).hotelRoomType(hotelRoomType6).paymentType(com.example.demo.model.enums.PaymentType.CASH).build(),
                RoomBooking.builder().pet(pet2).startDate(LocalDate.now()).endDate(LocalDate.now().plusDays(4)).hotelRoomType(hotelRoomType7).paymentType(com.example.demo.model.enums.PaymentType.ZALO).build(),
                RoomBooking.builder().pet(pet3).startDate(LocalDate.now()).endDate(LocalDate.now().plusDays(1)).hotelRoomType(hotelRoomType6).paymentType(com.example.demo.model.enums.PaymentType.CASH).build(),
                RoomBooking.builder().pet(pet2).startDate(LocalDate.now().plusDays(4)).endDate(LocalDate.now().plusDays(3)).hotelRoomType(hotelRoomType1).paymentType(com.example.demo.model.enums.PaymentType.ZALO).build(),
                RoomBooking.builder().pet(pet3).startDate(LocalDate.now().plusDays(2)).endDate(LocalDate.now().plusDays(2)).hotelRoomType(hotelRoomType9).paymentType(com.example.demo.model.enums.PaymentType.ZALO).build(),
                RoomBooking.builder().pet(pet1).startDate(LocalDate.now().plusDays(1)).endDate(LocalDate.now().plusDays(1)).hotelRoomType(hotelRoomType2).paymentType(com.example.demo.model.enums.PaymentType.CASH).build(),
                RoomBooking.builder().pet(pet5).startDate(LocalDate.now().plusDays(3)).endDate(LocalDate.now().plusDays(2)).hotelRoomType(hotelRoomType3).paymentType(com.example.demo.model.enums.PaymentType.ZALO).build(),
                RoomBooking.builder().pet(pet1).startDate(LocalDate.now().plusDays(1)).endDate(LocalDate.now().plusDays(1)).hotelRoomType(hotelRoomType9).paymentType(com.example.demo.model.enums.PaymentType.CASH).build(),
                RoomBooking.builder().pet(pet6).startDate(LocalDate.now().plusDays(2)).endDate(LocalDate.now().plusDays(2)).hotelRoomType(hotelRoomType7).paymentType(com.example.demo.model.enums.PaymentType.ZALO).build());
        roomBookingRepository.saveAll(roomBookings);
    }


}
