package com.example.cozastore.payload.response;

public class SizeResponse {
    private String id;
    private String sizeName;

    public SizeResponse() {
    }

    public SizeResponse(String id, String sizeName) {
        this.id = id;
        this.sizeName = sizeName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSizeName() {
        return sizeName;
    }

    public void setSizeName(String sizeName) {
        this.sizeName = sizeName;
    }
}
