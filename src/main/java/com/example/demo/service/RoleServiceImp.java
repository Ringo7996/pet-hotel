package com.example.demo.service;

import com.example.demo.exception.NotFoundException;
import com.example.demo.model.entity.Role;
import com.example.demo.model.entity.User;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImp implements RoleService {
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserService userService;

    @Override
    public boolean isRootAdmin(Integer userId) {
        Role rootAdmin = roleRepository.findByName("ROOT_ADMIN").orElse(null);
        User user = userService.findById(userId);
        return user.getRoles().contains(rootAdmin);
    }

    @Override
    public boolean isAdmin(Integer userId) {
        Role admin = roleRepository.findByName("ADMIN").orElse(null);
        User user = userService.findById(userId);
        return user.getRoles().contains(admin);
    }
}
