package com.example.cozastore.payload.request;

public class CategoryRequest {
    private String nameCategory;

    public CategoryRequest() {
    }

    public CategoryRequest(String nameCategory) {
        this.nameCategory = nameCategory;
    }

    public String getNameCategory() {
        return nameCategory;
    }

    public void setNameCategory(String nameCategory) {
        this.nameCategory = nameCategory;
    }
}
