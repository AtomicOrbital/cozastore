package com.example.cozastore.service.imp;

import com.example.cozastore.payload.request.SignUpRequest;

public interface UserServiceImp {
    boolean insertUser(SignUpRequest signUpRequest);
}
