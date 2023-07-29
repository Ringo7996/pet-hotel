package com.example.demo.service;


import com.example.demo.exception.ExitsUserException;
import com.example.demo.exception.NotFoundException;
import com.example.demo.model.entity.Role;
import com.example.demo.model.entity.TokenConfirm;
import com.example.demo.model.entity.User;
import com.example.demo.model.request.CreateUserRequest;
import com.example.demo.model.request.UpdateUserRequest;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.TokenConfirmRepository;
import com.example.demo.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
                .build();

        userRepository.save(newUser);

        return newUser;
    }

    @Override
    public void updateInfo(UpdateUserRequest request,HttpSession session) {
        String email = (String) session.getAttribute("MY_SESSION");
        User userSystem = userRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("User is not found"));
        Optional<User> user = userRepository.findByEmail(request.getEmail());
        if(user.isPresent() && request.getEmail().equalsIgnoreCase(email) || !user.isPresent()){
            userSystem.setEmail(request.getEmail());
            userSystem.setName(request.getName());
            userSystem.setPhone(request.getPhone());
            userRepository.save(userSystem);
            session.setAttribute("MY_SESSION",request.getEmail());
        }
        else throw  new ExitsUserException("Email already exists");
    }

    @Override
    public void resetPw(String email, String encodedPassword) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("User is not found"));
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
    public User getAnUser(Integer userId) {
        return userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User is not found"));
    }


}