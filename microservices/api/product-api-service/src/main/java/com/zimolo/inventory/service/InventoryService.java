package com.zimolo.inventory.service;

import com.zimolo.inventory.domain.Product;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface InventoryService {

    List<Product> importProduct(String category_id, int quantity) throws Exception;

    void uploadImage(MultipartFile file, String category_id) throws IOException;

    void deleteProduct(String id) throws Exception;
}
