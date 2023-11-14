package com.example.cozastore.service;

import com.example.cozastore.entity.ColorEntity;
import com.example.cozastore.payload.request.ColorRequest;
import com.example.cozastore.payload.response.ColorResponse;
import com.example.cozastore.payload.response.SizeResponse;
import com.example.cozastore.repository.ColorRepository;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


@Service
public class ColorService implements com.example.cozastore.service.imp.ColorServiceImp {
    @Autowired
    private ColorRepository colorRepository;

    @Autowired
    private RedisTemplate redisTemplate;

    private Logger logger = LoggerFactory.getLogger(ColorService.class);

    private Gson gson = new Gson();

    @Override
    public ColorResponse createColor(ColorRequest colorRequest) {
        ColorEntity colorEntity= new ColorEntity();
        colorEntity.setName(colorRequest.getColorName());
        ColorEntity savedEntity = colorRepository.save(colorEntity);
        return new ColorResponse(Integer.toString(savedEntity.getId()), savedEntity.getName());
    }

    @Override
    public List<ColorResponse> getAllColors() {
        List<ColorResponse> colorsResponses = new ArrayList<>();
        // Lấy danh sách tất cả ColorEntity từ cơ sở dữ liệu
        if(redisTemplate.hasKey("listColor")){
            logger.info("Kiem tra cache listColor");
            String dataRedis = redisTemplate.opsForValue().get("listColor").toString();
            logger.info("Fetched from Redis: " + dataRedis);
            Type listType = new TypeToken<ArrayList<ColorResponse>>(){}.getType();
            colorsResponses = gson.fromJson(dataRedis, listType);
        } else {
            List<ColorEntity> colorEntities = colorRepository.findAll();

            //Duyệt qua danh sách các ColorEntity và chuyển đổi từng thực thể sang DTO
            for(ColorEntity colorEntity:colorEntities){
                colorsResponses.add(new ColorResponse(Integer.toString(colorEntity.getId()), colorEntity.getName()));
            }
            // Sử dụng stream với cách viết rút gọn
//        return colorRepository.findAll().stream()
//                .map(colorEntity -> new ColorResponse(Integer.toString(colorEntity.getId()), colorEntity.getName()))
//                .collect(Collectors.toList());
            String dataJson = gson.toJson(colorsResponses);
            logger.info("Saving to Redis: " + dataJson);
            redisTemplate.opsForValue().set("listColor",dataJson);
        }

        return colorsResponses;
    }

    @Override
    public ColorResponse getColorById(int id) {
        ColorEntity colorEntity =  colorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Color not found"));
        return new ColorResponse(Integer.toString(colorEntity.getId()), colorEntity.getName());
    }

    @Override
    public ColorResponse updateColor(int id, ColorRequest colorRequest) {
        ColorEntity existingColor = colorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Color not found"));
        existingColor.setName(colorRequest.getColorName());
        ColorEntity updatedEntity = colorRepository.save(existingColor);
        return new ColorResponse(Integer.toString(updatedEntity.getId()), updatedEntity.getName());
    }

    @Override
    public void deleteColor(int id) {
        colorRepository.deleteById(id);
    }
}
