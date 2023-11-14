package com.example.cozastore.payload.response;

public class RoleResponse {
    private int id;
    private String roleName;

    public RoleResponse() {
    }

    public RoleResponse(int id, String roleName) {
        this.id = id;
        this.roleName = roleName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
