package com.example.cozastore.controller;

import com.example.cozastore.payload.request.SignUpRequest;
import com.example.cozastore.payload.response.BaseResponse;
import com.example.cozastore.service.imp.UserServiceImp;
import com.example.cozastore.utils.JwtHelper;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    private Gson gson = new Gson();

    @PostMapping("/signin")
    public ResponseEntity<?>signin(@RequestParam String username, @RequestParam String password){
        UsernamePasswordAuthenticationToken authen = new UsernamePasswordAuthenticationToken(username,password);
        Authentication authentication = authenticationManager.authenticate(authen);

        List<GrantedAuthority>  listRoles = (List<GrantedAuthority>) authentication.getAuthorities();
        String dataToken = gson.toJson(listRoles);

        String token = jwtHelper.generateToken(dataToken);
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setData(token);

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
}
