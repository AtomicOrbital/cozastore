package com.example.cozastore.controller;

import com.example.cozastore.entity.UserEntity;
import com.example.cozastore.payload.request.SignUpRequest;
import com.example.cozastore.payload.request.UserRequest;
import com.example.cozastore.payload.response.BaseResponse;
import com.example.cozastore.payload.response.SignInResponse;
import com.example.cozastore.payload.response.UserResponse;
import com.example.cozastore.service.UserService;
import com.example.cozastore.service.imp.UserServiceImp;
import com.example.cozastore.utils.JwtHelper;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;
@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private JwtHelper jwtHelper;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserServiceImp userServiceImp;

    @Autowired
    private Gson gson = new Gson();

    @PostMapping("/signin")
    public ResponseEntity<?>signin(@RequestParam String username, @RequestParam String password){
        UsernamePasswordAuthenticationToken authen = new UsernamePasswordAuthenticationToken(username,password);
        Authentication authentication = authenticationManager.authenticate(authen);

        List<GrantedAuthority>  listRoles = (List<GrantedAuthority>) authentication.getAuthorities();
        String dataToken = gson.toJson(listRoles);
        String token = jwtHelper.generateToken(dataToken);

        List<String> roles = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        String role = roles.isEmpty() ? null : roles.get(0);

        BaseResponse baseResponse = new BaseResponse();
        SignInResponse signInResponse = new SignInResponse(token, role);
        baseResponse.setData(signInResponse);
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@Valid @RequestBody SignUpRequest signUpRequest){
        boolean isSuccess = userServiceImp.insertUser(signUpRequest);

        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setMessage(isSuccess ? "Thêm thành công" : "Thêm thất bại");
        baseResponse.setData(isSuccess);

        HttpStatus status = isSuccess ? HttpStatus.OK : HttpStatus.BAD_REQUEST;

        return new ResponseEntity<>(baseResponse, status);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping
    public ResponseEntity<BaseResponse> getAllUsers(){
        BaseResponse baseResponse = new BaseResponse();
        try {
            List<UserResponse> users = userServiceImp.getAllUsers();
            baseResponse.setMessage("SUCCESS");
            baseResponse.setData(users);
            return ResponseEntity.ok(baseResponse);
        }catch (Exception e){
            baseResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());
            baseResponse.setMessage(e.getMessage());
            return ResponseEntity.badRequest().body(baseResponse);
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse> getUserById(@PathVariable int id){
        BaseResponse baseResponse = new BaseResponse();
        try {
            UserResponse userResponse = userServiceImp.getUserById(id);
            baseResponse.setMessage("SUCCESS");
            baseResponse.setData(userResponse);
            return ResponseEntity.ok(baseResponse);
        }catch (Exception e){
            baseResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());
            baseResponse.setMessage(e.getMessage());
            return ResponseEntity.badRequest().body(baseResponse);
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<BaseResponse> updateUser(@PathVariable int id, @RequestBody UserRequest userRequest){
        BaseResponse baseResponse = new BaseResponse();

        try {
            UserResponse updatedUser = userServiceImp.updateUser(id, userRequest);
            baseResponse.setMessage("Updated User Successfully");
            baseResponse.setData(updatedUser);
            return ResponseEntity.ok(baseResponse);
        }catch (Exception e){
            baseResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());
            baseResponse.setMessage(e.getMessage());
            return ResponseEntity.badRequest().body(baseResponse);
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse> deleteUser(@PathVariable int id){
        BaseResponse baseResponse = new BaseResponse();

        try {
            userServiceImp.deleteUser(id);
            baseResponse.setMessage("Deleted User Successfully");
            return ResponseEntity.ok(baseResponse);
        }catch (Exception e){
            baseResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());
            baseResponse.setMessage(e.getMessage());
            return ResponseEntity.badRequest().body(baseResponse);
        }
    }
}
