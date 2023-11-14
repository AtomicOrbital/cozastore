package com.example.cozastore.service.imp;

import com.example.cozastore.entity.UserEntity;
import com.example.cozastore.payload.request.SignUpRequest;
import com.example.cozastore.payload.request.UserRequest;
import com.example.cozastore.payload.response.UserResponse;

import java.util.List;

public interface UserServiceImp {
    boolean insertUser(SignUpRequest signUpRequest);
    List<UserResponse> getAllUsers();
    UserResponse getUserById(int id);
    UserResponse updateUser(int id, UserRequest userRequest);
    void deleteUser(int id);
}
