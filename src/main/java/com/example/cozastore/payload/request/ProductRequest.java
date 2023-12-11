package com.example.cozastore.payload.request;

import org.springframework.web.multipart.MultipartFile;

public class ProductRequest {
    private String images;
    private String title;
    private float price;
    private int idCategory;
    private String tags;

    public ProductRequest() {
    }

    public ProductRequest(String images, String title, float price, int idCategory, String tags) {
        this.images = images;
        this.title = title;
        this.price = price;
        this.idCategory = idCategory;
        this.tags = tags;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(int idCategory) {
        this.idCategory = idCategory;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }
}
