package com.example.cozastore.service;


import com.example.cozastore.entity.TagEntity;
import com.example.cozastore.payload.request.TagRequest;
import com.example.cozastore.payload.response.TagResponse;
import com.example.cozastore.repository.TagRepository;
import com.example.cozastore.service.imp.TagServiceImp;
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
public class TagService implements TagServiceImp {
    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private RedisTemplate redisTemplate;

    private Logger logger = LoggerFactory.getLogger(TagService.class);

    private Gson gson = new Gson();
    @Override
    public TagResponse createTag(TagRequest tagRequest) {
        TagEntity tagEntity = new TagEntity();
        tagEntity.setNameTag(tagRequest.getNameTag());
        TagEntity savedEntity = tagRepository.save(tagEntity);
        return new TagResponse(savedEntity.getId(), savedEntity.getNameTag());
    }

    @Override
    public List<TagResponse> getAllTags() {
        List<TagResponse> tagResponses = new ArrayList<>();
        if(redisTemplate.hasKey("listTag")){
            logger.info("Kiem tra cache listTag");
            String dataRedis = redisTemplate.opsForValue().get("listTag").toString();
            logger.info("Fetched from Redis: " + dataRedis);
            Type listType = new TypeToken<ArrayList<TagResponse>>(){}.getType();
            tagResponses = gson.fromJson(dataRedis, listType);
        } else {
            logger.info("Kiem tra tag database no cache");
            List<TagEntity> tagEntities = tagRepository.findAll();
            for(TagEntity tagEntity:tagEntities){
                tagResponses.add(new TagResponse(tagEntity.getId(), tagEntity.getNameTag()));
            }
            String dataJson = gson.toJson(tagResponses);
            logger.info("Saving to Redis: " + dataJson);
            redisTemplate.opsForValue().set("listTag",dataJson);
        }

        return tagResponses;
    }

    @Override
    public TagResponse getTagById(int id) {
        TagEntity tagEntity = tagRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Tag not found"));
        return new TagResponse(tagEntity.getId(), tagEntity.getNameTag());
    }

    @Override
    public TagResponse updateTag(int id, TagRequest tagRequest) {
        TagEntity existingTag = tagRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tag not found"));
        existingTag.setNameTag(tagRequest.getNameTag());
        TagEntity tagEntity = tagRepository.save(existingTag);
        return new TagResponse(tagEntity.getId(), tagEntity.getNameTag());
    }

    @Override
    public void deleteTag(int id) {
        tagRepository.deleteById(id);
    }
}
