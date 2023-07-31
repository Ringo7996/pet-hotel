package com.example.demo.service;

public interface RoleService {
    boolean isRootAdmin(Integer userId);

    boolean isAdmin(Integer userId);
}
