package com.example.cozastore.service.imp;

import com.example.cozastore.payload.request.ColorRequest;
import com.example.cozastore.payload.request.SizeRequest;
import com.example.cozastore.payload.response.ColorResponse;
import com.example.cozastore.payload.response.SizeResponse;

import java.util.List;

public interface SizeServiceImp {
    SizeResponse createSize(SizeRequest sizeRequest);
    List<SizeResponse> getAllSizes();
    SizeResponse getSizeById(int id);
    SizeResponse updateSize(int id, SizeRequest sizeRequest);
    void deleteSize(int id);
}
