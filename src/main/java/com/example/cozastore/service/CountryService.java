package com.example.cozastore.service;


import com.example.cozastore.entity.CountryEntity;
import com.example.cozastore.entity.TagEntity;
import com.example.cozastore.payload.request.CountryRequest;
import com.example.cozastore.payload.response.CountryResponse;
import com.example.cozastore.payload.response.SizeResponse;
import com.example.cozastore.payload.response.TagResponse;
import com.example.cozastore.repository.CountryRepository;
import com.example.cozastore.service.imp.CountryServiceImp;
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
public class CountryService implements CountryServiceImp {
    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private RedisTemplate redisTemplate;

    private Logger logger = LoggerFactory.getLogger(CountryService.class);

    private Gson gson = new Gson();


    @Override
    public CountryResponse createCountry(CountryRequest countryRequest) {
        CountryEntity countryEntity = new CountryEntity();
        countryEntity.setNameCountry(countryRequest.getNameCountry());
        CountryEntity savedEntity = countryRepository.save(countryEntity);
        return new CountryResponse(savedEntity.getId(), savedEntity.getNameCountry());
    }

    @Override
    public List<CountryResponse> getAllCountry() {
        List<CountryResponse> countryResponses = new ArrayList<>();
        if(redisTemplate.hasKey("listCountry")){
            logger.info("Kiem tra cache listCountry");
            String dataRedis = redisTemplate.opsForValue().get("listCountry").toString();
            logger.info("Fetched from Redis: " + dataRedis);
            Type listType = new TypeToken<ArrayList<CountryResponse>>(){}.getType();
            countryResponses = gson.fromJson(dataRedis, listType);
        } else {
            List<CountryEntity> countryEntities = countryRepository.findAll();

            for(CountryEntity countryEntity:countryEntities){
                countryResponses.add(new CountryResponse(countryEntity.getId(), countryEntity.getNameCountry()));
            }
            String dataJson = gson.toJson(countryResponses);
            logger.info("Saving to Redis: " + dataJson);
            redisTemplate.opsForValue().set("listCountry",dataJson);
        }

        return countryResponses;
    }

    @Override
    public CountryResponse getCountryById(int id) {
        CountryEntity countryEntity = countryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Country not found"));
        return new CountryResponse(countryEntity.getId(), countryEntity.getNameCountry());
    }

    @Override
    public CountryResponse updateCountry(int id, CountryRequest countryRequest) {
        CountryEntity existingCountry = countryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Country not found"));
        existingCountry.setNameCountry(countryRequest.getNameCountry());
        CountryEntity countryEntity = countryRepository.save(existingCountry);
        return new CountryResponse(countryEntity.getId(), countryEntity.getNameCountry());
    }

    @Override
    public void deleteCountry(int id) {
        countryRepository.deleteById(id);
    }
}
