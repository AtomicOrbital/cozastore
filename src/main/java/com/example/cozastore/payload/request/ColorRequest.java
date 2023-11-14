package com.example.cozastore.payload.request;

public class ColorRequest {
    private String colorName;

    public ColorRequest() {
    }

    public ColorRequest(String colorName) {
        this.colorName = colorName;
    }

    public String getColorName() {
        return colorName;
    }

    public void setColorName(String colorName) {
        this.colorName = colorName;
    }
}
