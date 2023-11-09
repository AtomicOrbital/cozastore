package com.example.cozastore.service.imp;

import com.example.cozastore.payload.request.ColorRequest;
import com.example.cozastore.payload.response.ColorResponse;

import java.util.List;

public interface ColorServiceImp {
    ColorResponse createColor(ColorRequest colorRequest);
    List<ColorResponse> getAllColors();
    ColorResponse getColorById(int id);
    ColorResponse updateColor(int id,ColorRequest colorRequest);
    void deleteColor(int id);
}
