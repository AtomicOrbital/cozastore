package com.example.cozastore.service.imp;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FileStorageServiceImp {
    boolean saveFile(MultipartFile file);

    Resource loadFile(String fileName);
}
