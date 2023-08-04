package com.example.demo.service;

import com.example.demo.model.entity.User;
import com.example.demo.model.request.CreateUserRequest;
import com.example.demo.model.request.UpdatePasswordRequest;
import com.example.demo.model.request.UpdateUserRequest;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.servlet.http.HttpSession;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.ui.Model;

import java.util.List;

public interface UserService {

    void sendResetPwEmail(String email);
    void updatePassword(UpdatePasswordRequest request, HttpSession session) throws Exception;

    User createUser(CreateUserRequest request);
    void updateInfo(UpdateUserRequest request, HttpSession session);

    void resetPw(String email, String encodedPassword);

    User findByEmail(String name);

    Page<User> getAllUsersWithPage(Pageable pageable);

    Page<User> getUsersByStatusWithPage(Boolean status,Pageable pageable);



    User findById(Integer userId);

    Model getUserLogin (HttpSession session, Model model);

    void createUserByAdmin(CreateUserRequest request) ;
    void updateUserByAdmin(UpdateUserRequest request,Integer id) ;

    void softDelete(Integer id);
    void activityUser(Integer id);

    List<User> getAdminNotPartOfHotel(Integer id);

    Boolean isActivity(Integer id);

}
