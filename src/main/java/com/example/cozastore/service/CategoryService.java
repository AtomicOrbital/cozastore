package com.example.cozastore.service;

import com.example.cozastore.entity.CategoryEntity;
import com.example.cozastore.payload.response.CategoryResponse;
import com.example.cozastore.repository.CategoryRepository;
import com.example.cozastore.service.imp.CategoryServiceImp;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService implements CategoryServiceImp {
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private RedisTemplate redisTemplate;

    private Logger logger = LoggerFactory.getLogger(CategoryService.class);

    private Gson gson = new Gson();

//    @Cacheable("allCategory")
    @Override
    public List<CategoryResponse> getAllCategory() {


        List<CategoryResponse> listResponse = new ArrayList<>();
        if(redisTemplate.hasKey("listCategory")){
            logger.info("Kiem tra cache redis");
            String dataRedis = redisTemplate.opsForValue().get("listCategory").toString();
            Type listType = new TypeToken<ArrayList<CategoryResponse>>(){}.getType();
            listResponse = gson.fromJson(dataRedis, listType);
        } else {
            logger.info("Kiem tra category database no cache");
            List<CategoryEntity> list = categoryRepository.findAll();
            for(CategoryEntity item: list){
                CategoryResponse categoryResponse = new CategoryResponse();
                categoryResponse.setId(item.getId());
                categoryResponse.setName(item.getName());

                listResponse.add(categoryResponse);
            }
            String dataJson = gson.toJson(listResponse);
            redisTemplate.opsForValue().set("listCategory",dataJson);
        }
        return listResponse;
    }
}
