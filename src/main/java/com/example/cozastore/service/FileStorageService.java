package com.example.cozastore.service;

import com.example.cozastore.service.imp.FileStorageServiceImp;
import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileStorageService implements FileStorageServiceImp {

    @Value("${path.upload.file}")
    private String folderPath;

    private Path root;

    private final Tika tika = new Tika();

    @Override
    public boolean saveFile(MultipartFile file) {
        boolean isSuccess = false;
        try {
            root = Paths.get(folderPath);
            if(!Files.exists(root)){
                Files.createDirectories(root);
            }
            Files.copy(file.getInputStream(),root.resolve(file.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);
            isSuccess = true;
        }catch (Exception e){
            System.out.println("Lỗi " + e.getLocalizedMessage());
        }

        return isSuccess;
    }

    @Override
    public Resource loadFile(String fileName) {
        try {
            root = Paths.get(folderPath);
            Path pathFile = root.resolve(fileName);
            Resource resource = new UrlResource(pathFile.toUri());
            if(resource.exists()){
                return resource;
            }
        } catch (Exception e) {
            System.out.println("Lỗi load file " + e.getLocalizedMessage());
        }
        return null;
    }

    public String getContentType(Path path){
        try {
            return tika.detect(path);
        }catch(Exception e){
            return "application/octet-stream";
        }
    }

    // Trả về đường dẫn cho filename
    public Path getFilePath(String fileName) {
        root = Paths.get(folderPath).normalize().toAbsolutePath();
        return root.resolve(fileName).normalize();
    }
}
