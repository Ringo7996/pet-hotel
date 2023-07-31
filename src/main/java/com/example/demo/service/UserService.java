package com.example.demo.service;

import com.example.demo.model.entity.Image;
import com.example.demo.model.entity.User;
import com.example.demo.model.request.CreateUserRequest;
import com.example.demo.model.request.UpdatePasswordRequest;
import com.example.demo.model.request.UpdateUserRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {

    void sendResetPwEmail(String email);
    void updatePassword(UpdatePasswordRequest request, HttpSession session) throws Exception;

    User createUser(CreateUserRequest request);
    void updateInfo(UpdateUserRequest request, HttpSession session);

    void resetPw(String email, String encodedPassword);

    User findByEmail(String name);

    Page<User> getAllUsersWithPage(Pageable pageable);

    User findById(Integer userId);


}
