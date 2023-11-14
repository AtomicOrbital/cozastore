package com.example.cozastore.service;

import com.example.cozastore.entity.RoleEntity;
import com.example.cozastore.entity.UserEntity;
import com.example.cozastore.payload.request.SignUpRequest;
import com.example.cozastore.payload.request.UserRequest;
import com.example.cozastore.payload.response.UserResponse;
import com.example.cozastore.repository.RoleRepository;
import com.example.cozastore.repository.UserRepository;
import com.example.cozastore.service.imp.UserServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService implements UserServiceImp {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public boolean insertUser(SignUpRequest signUpRequest) {
        boolean isSuccess = false;
        UserEntity user = new UserEntity();
        user.setEmail(signUpRequest.getUsername());
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));

        // Set mặc định cho user khi đăng ký có role mặc định là user
        RoleEntity defaultRole = roleService.getRoleByName("ROLE_USER");
        user.setRole(defaultRole);

        try {
            userRepository.save(user);
            isSuccess = true; // Set isSuccess to true if save operation is successful
        } catch (Exception e) {
            System.out.println("Lỗi validate" + e.getLocalizedMessage());
        }
        return isSuccess;
    }

    @Override
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream()
                .map(userEntity -> new UserResponse(userEntity.getId(),userEntity.getEmail(),userEntity.getPassword(), userEntity.getRole().getName()))
                .collect(Collectors.toList());
    }

    @Override
    public UserResponse getUserById(int id) {
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return new UserResponse(userEntity.getId(), userEntity.getEmail(), userEntity.getPassword() ,userEntity.getRole().getName());
    }

    @Override
    public UserResponse updateUser(int id, UserRequest userRequest) {
        UserEntity existingUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        existingUser.setEmail(userRequest.getUsername());
        existingUser.setPassword(passwordEncoder.encode(userRequest.getPassword()));

        RoleEntity role = roleService.getRoleByName(userRequest.getRole());
        existingUser.setRole(role);

        UserEntity updatedUser = userRepository.save(existingUser);
        return new UserResponse(updatedUser.getId(),updatedUser.getEmail(), updatedUser.getPassword(), updatedUser.getRole().getName());
    }

    @Override
    public void deleteUser(int id) {
        userRepository.deleteById(id);
    }
}
