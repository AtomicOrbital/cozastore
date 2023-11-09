package com.example.cozastore.payload.request;

public class CountryRequest {
    private String nameCountry;

    public CountryRequest() {
    }

    public CountryRequest(String nameCountry) {
        this.nameCountry = nameCountry;
    }

    public String getNameCountry() {
        return nameCountry;
    }

    public void setNameCountry(String nameCountry) {
        this.nameCountry = nameCountry;
    }
}
