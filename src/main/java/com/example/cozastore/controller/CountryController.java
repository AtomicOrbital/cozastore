package com.example.cozastore.controller;

import com.example.cozastore.payload.request.ColorRequest;
import com.example.cozastore.payload.request.CountryRequest;
import com.example.cozastore.payload.response.BaseResponse;
import com.example.cozastore.payload.response.ColorResponse;
import com.example.cozastore.payload.response.CountryResponse;
import com.example.cozastore.service.imp.CountryServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/country")
public class CountryController {
    @Autowired
    private CountryServiceImp countryServiceImp;

    @PostMapping
    public ResponseEntity<BaseResponse> createCountry(@RequestBody CountryRequest countryRequest){
        BaseResponse baseResponse = new BaseResponse();
        try {
            CountryResponse countryResponse = countryServiceImp.createCountry(countryRequest);
            baseResponse.setData(countryResponse);
            baseResponse.setMessage("Country created successfully");
            return ResponseEntity.ok(baseResponse);
        } catch(Exception e){
            baseResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());
            baseResponse.setMessage(e.getMessage());
            return ResponseEntity.badRequest().body(baseResponse);
        }
    }

    @GetMapping
    public ResponseEntity<BaseResponse> getAllCountry(){
        BaseResponse baseResponse = new BaseResponse();
        try {
            List<CountryResponse> country = countryServiceImp.getAllCountry();
            baseResponse.setMessage("SUCCESS");
            baseResponse.setData(country);
            return ResponseEntity.ok(baseResponse);
        }catch (Exception e){
            baseResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());
            baseResponse.setMessage(e.getMessage());
            return ResponseEntity.badRequest().body(baseResponse);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse> getCountryById(@PathVariable int id){
        BaseResponse baseResponse = new BaseResponse();
        try {
            CountryResponse countryResponse = countryServiceImp.getCountryById(id);
            baseResponse.setMessage("SUCCESS");
            baseResponse.setData(countryResponse);
            return ResponseEntity.ok(baseResponse);
        }catch (Exception e){
            baseResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());
            baseResponse.setMessage(e.getMessage());
            return ResponseEntity.badRequest().body(baseResponse);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<BaseResponse> updateCountry(@PathVariable int id, @RequestBody CountryRequest countryRequest){

        BaseResponse baseResponse = new BaseResponse();
        try {
            CountryResponse updatedCountry = countryServiceImp.updateCountry(id, countryRequest);
            baseResponse.setMessage("Color Updated Successfully");
            baseResponse.setData(updatedCountry);
            return ResponseEntity.ok(baseResponse);
        }catch (Exception e){
            baseResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());
            baseResponse.setMessage(e.getMessage());
            return ResponseEntity.badRequest().body(baseResponse);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse> deleteCountry(@PathVariable int id){
        BaseResponse baseResponse = new BaseResponse();
        try {
            countryServiceImp.deleteCountry(id);
            baseResponse.setMessage("Country Deleted Successfully");
            return ResponseEntity.ok(baseResponse);
        }catch (Exception e){
            baseResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());
            baseResponse.setMessage(e.getMessage());
            return ResponseEntity.badRequest().body(baseResponse);
        }
    }
}
