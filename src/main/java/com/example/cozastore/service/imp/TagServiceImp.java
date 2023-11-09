package com.example.cozastore.service.imp;

import com.example.cozastore.payload.request.SizeRequest;
import com.example.cozastore.payload.request.TagRequest;
import com.example.cozastore.payload.response.SizeResponse;
import com.example.cozastore.payload.response.TagResponse;

import java.util.List;

public interface TagServiceImp {
    TagResponse createTag(TagRequest tagRequest);
    List<TagResponse> getAllTags();
    TagResponse getTagById(int id);
    TagResponse updateTag(int id, TagRequest tagRequest);
    void deleteTag(int id);
}
