package com.example.cozastore.payload.response;

public class TagResponse {
    private int id;
    private String nameTag;

    public TagResponse() {
    }

    public TagResponse(int id, String nameTag) {
        this.id = id;
        this.nameTag = nameTag;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameTag() {
        return nameTag;
    }

    public void setNameTag(String nameTag) {
        this.nameTag = nameTag;
    }
}
