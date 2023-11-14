package com.example.cozastore.payload.request;

public class TagRequest {
    private String nameTag;

    public TagRequest() {
    }

    public TagRequest(String nameTag) {
        this.nameTag = nameTag;
    }

    public String getNameTag() {
        return nameTag;
    }

    public void setNameTag(String nameTag) {
        this.nameTag = nameTag;
    }
}
