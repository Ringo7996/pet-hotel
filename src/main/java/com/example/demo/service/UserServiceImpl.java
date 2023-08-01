package com.example.demo.service;


import com.example.demo.exception.ExitsUserException;
import com.example.demo.exception.NotFoundException;
import com.example.demo.model.entity.Image;
import com.example.demo.model.entity.Role;
import com.example.demo.model.entity.TokenConfirm;
import com.example.demo.model.entity.User;
import com.example.demo.model.request.CreateUserRequest;
import com.example.demo.model.request.UpdatePasswordRequest;
import com.example.demo.model.request.UpdateUserRequest;
import com.example.demo.repository.ImageRepository;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.TokenConfirmRepository;
import com.example.demo.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private TokenConfirmRepository tokenConfirmRepository;

    @Autowired
    private MailService mailService;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    ImageService imageService;

    @Override
    public void sendResetPwEmail(String email) {
        // tra email co ton tai trong database ko.
        User user = userRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("User is not found"));

        TokenConfirm token = TokenConfirm.builder()
                .user(user)
                .createdAt(LocalDateTime.now())
                .expiredAt(LocalDateTime.now().plusMinutes(10))
                .token(UUID.randomUUID().toString())
                .build();

        // tao ra token -> luu vao co so du lieu
        tokenConfirmRepository.save(token);

        // send email chua token
        // link: http://localhost:8080/reset-password/{token}
        String resetPwURL = "http://localhost:8080/reset-password/" + token.getToken();

        mailService.sendMail(
                user.getEmail(),
                "Reset Password",
                "Click this link to reset password " + resetPwURL
        );
    }

    @Override
    public User createUser(CreateUserRequest request) {
        Role userRole = roleRepository.findByName("USER").orElse(null);

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new NotFoundException("User already exists");
        }
        User newUser = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(encoder.encode(request.getPassword()))
                .phone(request.getPhone())
                .roles(List.of(userRole))
                .status(true)
                .build();

        userRepository.save(newUser);
        return newUser;
    }

    @Override
    public void createUserByAdmin(CreateUserRequest request){
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new NotFoundException("User already exists");
        }
        List<Role> roles = convertInToRole(request.getRoles());

        User newUser = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(encoder.encode(request.getPassword()))
                .phone(request.getPhone())
                .roles(roles)
                .status(true)
                .build();

        if(request.getFile() != null ){
           try {
               MultipartFile file = request.getFile();
               imageService.validateFile(file);
               Image image2upload = Image.builder()
                       .type(file.getContentType())
                       .data(file.getBytes())
                       .user(newUser)
                       .build();
               newUser.setImage(image2upload);
               imageRepository.save(image2upload);
           } catch (Exception e){
               throw new RuntimeException(e.toString());
           }
        }
        userRepository.save(newUser);
    }

    @Override
    public void updateUserByAdmin(UpdateUserRequest request,Integer id) {
        User user =findById(id);
        if(!user.getEmail().equalsIgnoreCase(request.getEmail()))
            if (userRepository.findByEmail(request.getEmail()).isPresent()) {
                throw new NotFoundException("User already exists");
            }
        List<Role> roles = convertInToRole(request.getRoles());

        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setName(request.getName());
        user.setRoles(roles);

        if(request.getFile() != null ){
            try {
                MultipartFile file = request.getFile();
                imageService.validateFile(file);
                Image image2upload = Image.builder()
                        .type(file.getContentType())
                        .data(file.getBytes())
                        .user(user)
                        .build();
                user.setImage(image2upload);
                imageRepository.save(image2upload);
            } catch (Exception e){
                throw new RuntimeException(e.toString());
            }
        }
        userRepository.save(user);
    }

    @Override
    public void updateInfo(UpdateUserRequest request, HttpSession session) {
        String email = (String) session.getAttribute("MY_SESSION");
        User userSystem = findByEmail(email);
        Optional<User> user = userRepository.findByEmail(request.getEmail());
        if (user.isEmpty() || request.getEmail().equalsIgnoreCase(email)) {
            userSystem.setEmail(request.getEmail());
            userSystem.setName(request.getName());
            userSystem.setPhone(request.getPhone());
            userRepository.save(userSystem);
            session.setAttribute("MY_SESSION", request.getEmail());
        } else throw new ExitsUserException("Email already exists");
    }

    public void updatePassword(UpdatePasswordRequest request, HttpSession session) throws Exception {
        String email = (String) session.getAttribute("MY_SESSION");
        User userSystem = findByEmail(email);
        if (encoder.matches(request.getOldPassword(), userSystem.getPassword())) {
            userSystem.setPassword(encoder.encode(request.getNewPassword()));
            userRepository.save(userSystem);
        } else throw new Exception("old password is not match");
    }


    @Override
    public void resetPw(String email, String encodedPassword) {
        User user = findByEmail(email);
        user.setPassword(encodedPassword);
        userRepository.save(user);
    }

    @Override
    public User findByEmail(String name) {
        return userRepository.findByEmail(name).orElseThrow(() -> new NotFoundException("User " + name + " is not found"));
    }


    @Override
    public Page<User> getAllUsersWithPage(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    public User findById(Integer userId) {
        return userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User is not found"));
    }

    @Override
    public Model getUserLogin(HttpSession session , Model model) {
        String email =(String) session.getAttribute("MY_SESSION");

        User user =findByEmail(email);
        model.addAttribute("USER",user);

        return model;
    }
    private List<Role> convertInToRole(List<Integer> list){
        List<Role> roles = new ArrayList<>();
        Role userRole;
        for (Integer i: list){
            if (i == 1) {
                userRole = roleRepository.findByName("USER").orElse(null);
            }else if(i == 2){
                userRole = roleRepository.findByName("ROOT_ADMIN").orElse(null);
            }else userRole = roleRepository.findByName("ADMIN").orElse(null);
            roles.add(userRole);
        }
        return  roles;
    }
}