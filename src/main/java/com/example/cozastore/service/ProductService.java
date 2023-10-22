package com.example.cozastore.service;

import com.example.cozastore.service.imp.ProductServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
@Service
public class ProductService implements ProductServiceImp {

    @Autowired
    private FileStorageService fileStorageService;


    @Override
    public boolean insertProduct(MultipartFile file,String title, double price, int idCategory) {
        boolean isSave =  fileStorageService.saveFile(file);
        if(isSave){
            // Khi save hình thành công thêm dữ liệu vào bảng Product và ProductDetail
            // Khi save dữ liệu database thành công thì thuộc tính id của Entity se có giá trị
            // Transaction
        }
        return false;
    }

    @Override
    public Resource downloadProductFile(String tenFile) {
        return fileStorageService.loadFile(tenFile);
    }


}
