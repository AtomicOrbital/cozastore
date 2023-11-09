package com.example.cozastore.payload.response;

public class CountryResponse {
    private int id;
    private String nameCountry;

    public CountryResponse() {
    }

    public CountryResponse(int id, String nameCountry) {
        this.id = id;
        this.nameCountry = nameCountry;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameCountry() {
        return nameCountry;
    }

    public void setNameCountry(String nameCountry) {
        this.nameCountry = nameCountry;
    }
}
