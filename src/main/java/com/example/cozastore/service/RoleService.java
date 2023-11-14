package com.example.cozastore.service;

import com.example.cozastore.entity.RoleEntity;
import com.example.cozastore.payload.response.RoleResponse;
import com.example.cozastore.repository.RoleRepository;
import com.example.cozastore.service.imp.RoleServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleService implements RoleServiceImp {
    @Autowired
    private RoleRepository roleRepository;

    @Cacheable(cacheNames = "roles", key = "'allRoles'")
    @Override
    public List<RoleResponse> getAllRoles() {
        return roleRepository.findAll().stream()
                .map(role -> new RoleResponse(role.getId(), role.getName()))
                .collect(Collectors.toList());
    }


    public RoleEntity getRoleByName(String roleName){
        return roleRepository.findByName(roleName)
                .orElseThrow(() -> new RuntimeException("Role not found: " + roleName));
    }
}
