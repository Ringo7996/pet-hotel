package com.example.demo.service;


import com.example.demo.exception.NotFoundException;
import com.example.demo.model.entity.TokenConfirm;
import com.example.demo.model.entity.User;
import com.example.demo.repository.TokenConfirmRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenConfirmRepository tokenConfirmRepository;

    @Autowired
    private MailService mailService;


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
    public void resetPw(String email, String encodedPassword) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("User is not found"));
        user.setPassword(encodedPassword);
        userRepository.save(user);
    }

    @Override
    public User findByEmail(String name) {
        return userRepository.findByEmail(name).orElseThrow(()-> new NotFoundException("User "+ name + " is not found"));
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
