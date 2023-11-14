package com.example.cozastore.service.imp;

import com.example.cozastore.entity.RoleEntity;
import com.example.cozastore.payload.response.RoleResponse;

import java.util.List;

public interface RoleServiceImp {
    List<RoleResponse>getAllRoles();
    RoleEntity getRoleByName(String roleName);
}
