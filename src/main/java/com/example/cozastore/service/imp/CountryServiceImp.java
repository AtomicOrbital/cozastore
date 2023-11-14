package com.example.cozastore.service.imp;

import com.example.cozastore.payload.request.CountryRequest;
import com.example.cozastore.payload.request.SizeRequest;
import com.example.cozastore.payload.response.CountryResponse;
import com.example.cozastore.payload.response.SizeResponse;

import java.util.List;

public interface CountryServiceImp {
    CountryResponse createCountry(CountryRequest countryRequest);
    List<CountryResponse> getAllCountry();
    CountryResponse getCountryById(int id);
    CountryResponse updateCountry(int id, CountryRequest countryRequest);
    void deleteCountry(int id);
}
