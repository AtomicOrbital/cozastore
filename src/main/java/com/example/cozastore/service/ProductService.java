package com.example.cozastore.service;

import com.example.cozastore.entity.CategoryEntity;
import com.example.cozastore.entity.ProductEntity;
import com.example.cozastore.payload.request.ProductRequest;
import com.example.cozastore.payload.response.ProductResponse;
import com.example.cozastore.repository.ProductRepository;
import com.example.cozastore.service.imp.ProductServiceImp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService implements ProductServiceImp {

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private ProductRepository productRepository;

    private final Logger logger = LoggerFactory.getLogger(ProductService.class);
    @Override
    public List<ProductResponse> getAllProducts() {
        List<ProductResponse> responseList = new ArrayList<>();
        List<ProductEntity> entityList = productRepository.findAll();
        for (ProductEntity product : entityList) {
            ProductResponse response = new ProductResponse();
            response.setId(product.getId());
            response.setTitle(product.getTitle());
            response.setImages(product.getImages());
            response.setPrice(product.getPrice());
            response.setTags(product.getTags());
            response.setIdCategory(product.getIdCategory().getId());
            responseList.add(response);
        }
        return responseList;
    }

    @Override
    public boolean insertProduct(MultipartFile file,
                                 String title,
                                 double price,
                                 int idCategory,
                                 String tags
                                 ) {
        boolean isSave =  fileStorageService.saveFile(file);
        if(isSave){
            try {
                // Khi save hình thành công thêm dữ liệu vào bảng Product và ProductDetail
                // Khi save dữ liệu database thành công thì thuộc tính id của Entity se có giá trị
                // Transaction
                ProductEntity product = new ProductEntity();
                product.setTitle(title);
                product.setPrice(price);

                // Lưu chỉ tên
                String fileName = file.getOriginalFilename();
                product.setImages(fileName);

                //Đặt Tags hoặc bỏ qua nếu không cần thiết
                product.setTags(tags);

                // Tạo và đặt CategoryEntity
                CategoryEntity category = new CategoryEntity();
                category.setId(idCategory);
                product.setIdCategory(category);

                productRepository.save(product);
                return true;
            }catch (Exception e){
                logger.error("Error when inserting product: " + e.getMessage());
                return false;
            }
        }
        return false;
    }

    @Override
    public Resource downloadProductFile(String tenFile) {
        return fileStorageService.loadFile(tenFile);
    }

    @Override
    public ProductResponse getProductById(int id) {
        ProductResponse response = new ProductResponse();
        Optional<ProductEntity> optionalProductResponse = productRepository.findById(id);
        if (optionalProductResponse.isPresent()){
            ProductEntity product = optionalProductResponse.get();
            response.setId(product.getId());
            response.setTitle(product.getTitle());
            response.setPrice(product.getPrice());
            response.setImages(product.getImages());
            response.setTags(product.getTags());
            response.setIdCategory(product.getIdCategory().getId());
        } else {
            logger.info("Can't find product from ID, please check your ID.");
        }
        return response;
    }

    @Override
    public boolean deleteProductById(int id) {
        boolean isSuccess = false;
        try {
            productRepository.deleteById(id);
            isSuccess = true;
        } catch (Exception e) {
            logger.info("Can't delete product. Please check your ID again");
        }
        return isSuccess;
    }

    @Override
    public boolean modifyProductById(int id,
                                     MultipartFile file,
                                     String title,
                                     double price,
                                     int idCategory,
                                     String tags) {
        boolean isSuccess = false;
        Optional<ProductEntity> optionalProductResponse = productRepository.findById(id);
        boolean isSaveFile = fileStorageService.saveFile(file);
        if (optionalProductResponse.isPresent() && isSaveFile){
            ProductEntity product = optionalProductResponse.get();
            product.setTitle(title);
            product.setPrice(price);

            if (file != null && !file.isEmpty()) {
                // Xử lý lưu trữ file
                String fileName = file.getOriginalFilename();
                product.setImages(fileName);
            }

            product.setTags(tags);
            CategoryEntity category = new CategoryEntity();
            category.setId(idCategory);
            product.setIdCategory(category);

            productRepository.save(product);
            isSuccess = true;
        } else {
            logger.info("Can't find product from ID, please check your ID.");
        }
        return isSuccess;
    }


}
