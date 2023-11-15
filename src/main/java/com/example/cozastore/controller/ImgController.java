package com.example.cozastore.controller;

import com.example.cozastore.service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/api/images")
public class ImgController {
    @Autowired
    private FileStorageService fileStorageService;

    @GetMapping("/{fileName}")
    public ResponseEntity<Resource> getImage(@PathVariable String fileName){
        Path filePath = fileStorageService.getFilePath(fileName);
        Resource file = fileStorageService.loadFile(filePath.toString());
        if(file != null && file.exists()){
            String contentType = fileStorageService.getContentType(filePath);
            return ResponseEntity
                    .ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + file.getFilename() + "\"")
                    .body(file);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
