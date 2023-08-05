package com.example.demo.model.projection;

import com.example.demo.model.entity.User;

import java.util.List;

public interface UserListInfo {
    Integer getId();

    String getEmail();

    String getName();

    String getPhone();

    Boolean getStatus();

    List<RoleInfo> getRoles();

    interface RoleInfo {
        Integer getId();

        String getName();

    }
}
