package com.example.cozastore.payload.response;

public class ColorResponse {
    private String id;
    private String colorName;

    public ColorResponse() {
    }

    public ColorResponse(String id, String colorName) {
        this.id = id;
        this.colorName = colorName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getColorName() {
        return colorName;
    }

    public void setColorName(String colorName) {
        this.colorName = colorName;
    }
}
