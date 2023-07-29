package com.example.demo.service;


import com.example.demo.model.entity.User;
import com.example.demo.model.request.CreateUserRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {

    void sendResetPwEmail(String email);

    User createUser(CreateUserRequest request);

    void resetPw(String email, String encodedPassword);

    User findByEmail(String name);

    Page<User> getAllUsersWithPage(Pageable pageable);

    User getAnUser(Integer userId);
}
