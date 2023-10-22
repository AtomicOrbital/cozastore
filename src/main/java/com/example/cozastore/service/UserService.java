package com.example.cozastore.service;

import com.example.cozastore.entity.UserEntity;
import com.example.cozastore.payload.request.SignUpRequest;
import com.example.cozastore.repository.UserRepository;
import com.example.cozastore.service.imp.UserServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserServiceImp {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public boolean insertUser(SignUpRequest signUpRequest) {
        boolean isSuccess = false;
        UserEntity user = new UserEntity();
        user.setEmail(signUpRequest.getUsername());
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        try {
            userRepository.save(user);
            isSuccess = true; // Set isSuccess to true if save operation is successful
        } catch (Exception e) {
            System.out.println("Lỗi validate" + e.getLocalizedMessage());
        }
        return isSuccess;
    }
}
