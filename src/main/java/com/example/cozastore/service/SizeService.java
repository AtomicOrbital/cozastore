package com.example.cozastore.service;

import com.example.cozastore.entity.ColorEntity;
import com.example.cozastore.entity.SizeEntity;
import com.example.cozastore.payload.request.SizeRequest;
import com.example.cozastore.payload.response.SizeResponse;
import com.example.cozastore.payload.response.TagResponse;
import com.example.cozastore.repository.SizeRepository;
import com.example.cozastore.service.imp.SizeServiceImp;
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
public class SizeService implements SizeServiceImp {
    @Autowired
    private SizeRepository sizeRepository;

    @Autowired
    private RedisTemplate redisTemplate;

    private Logger logger = LoggerFactory.getLogger(SizeService.class);

    private Gson gson = new Gson();
    @Override
    public SizeResponse createSize(SizeRequest sizeRequest) {
        SizeEntity sizeEntity = new SizeEntity();
        sizeEntity.setName(sizeRequest.getSizeName());
        SizeEntity savedEntity = sizeRepository.save(sizeEntity);
        return new SizeResponse(Integer.toString(savedEntity.getId()), savedEntity.getName());
    }

    @Override
    public List<SizeResponse> getAllSizes() {
        List<SizeResponse> sizeResponses = new ArrayList<>();
        if(redisTemplate.hasKey("listSize")){
            logger.info("Kiem tra cache listSize");
            String dataRedis = redisTemplate.opsForValue().get("listSize").toString();
            logger.info("Fetched from Redis: " + dataRedis);
            Type listType = new TypeToken<ArrayList<SizeResponse>>(){}.getType();
            sizeResponses = gson.fromJson(dataRedis, listType);
        } else {
            // Lấy danh sách tất cả SizeEntity tư cơ sở dư liệu
            List<SizeEntity> sizeEntities = sizeRepository.findAll();

            for(SizeEntity sizeEntity:sizeEntities){
                sizeResponses.add(new SizeResponse(Integer.toString(sizeEntity.getId()), sizeEntity.getName()));
            }
            String dataJson = gson.toJson(sizeResponses);
            logger.info("Saving to Redis: " + dataJson);
            redisTemplate.opsForValue().set("listSize",dataJson);
        }

        return sizeResponses;
    }

    @Override
    public SizeResponse getSizeById(int id) {
        SizeEntity sizeEntity = sizeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Size not found"));
        return new SizeResponse(Integer.toString(sizeEntity.getId()), sizeEntity.getName());
    }

    @Override
    public SizeResponse updateSize(int id, SizeRequest sizeRequest) {
        SizeEntity existingSize = sizeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Size not found"));
        existingSize.setName(sizeRequest.getSizeName());
        SizeEntity sizeEntity = sizeRepository.save(existingSize);
        return new SizeResponse(Integer.toString(sizeEntity.getId()), sizeEntity.getName());
    }

    @Override
    public void deleteSize(int id) {
        sizeRepository.deleteById(id);
    }
}
