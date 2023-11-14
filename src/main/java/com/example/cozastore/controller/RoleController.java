package com.example.cozastore.controller;

import com.example.cozastore.payload.response.BaseResponse;
import com.example.cozastore.payload.response.RoleResponse;
import com.example.cozastore.payload.response.TagResponse;
import com.example.cozastore.service.imp.RoleServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@PreAuthorize("hasRole('ROLE_ADMIN')")
@RequestMapping("/api/roles")
public class RoleController {
    @Autowired
    private RoleServiceImp roleServiceImp;


    @GetMapping
    public ResponseEntity<BaseResponse> getAllRoles(){

        BaseResponse baseResponse = new BaseResponse();
        try {
            List<RoleResponse> roles = roleServiceImp.getAllRoles();
            baseResponse.setData(roles);
            baseResponse.setMessage("SUCCESS");
            return ResponseEntity.ok(baseResponse);
        }catch (Exception e){
            baseResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());
            baseResponse.setMessage(e.getMessage());
            return ResponseEntity.badRequest().body(baseResponse);
        }
    }
 }
