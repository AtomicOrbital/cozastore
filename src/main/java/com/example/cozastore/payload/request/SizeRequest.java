package com.example.cozastore.payload.request;

public class SizeRequest {
    private String sizeName;

    public SizeRequest() {
    }

    public SizeRequest(String sizeName) {
        this.sizeName = sizeName;
    }

    public String getSizeName() {
        return sizeName;
    }

    public void setSizeName(String sizeName) {
        this.sizeName = sizeName;
    }
}
