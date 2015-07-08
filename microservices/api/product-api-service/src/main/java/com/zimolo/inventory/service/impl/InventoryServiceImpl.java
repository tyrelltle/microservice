package com.zimolo.inventory.service.impl;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.zimolo.inventory.domain.Category;
import com.zimolo.inventory.domain.Product;
import com.zimolo.inventory.repository.CategoryRepository;
import com.zimolo.inventory.repository.ProductRepository;
import com.zimolo.inventory.service.InventoryService;
import com.zimolo.inventory.service.util.AutoIncrementer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.inject.Inject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class InventoryServiceImpl implements InventoryService {

    @Inject
    AutoIncrementer autoIncrementer;

    @Inject
    CategoryRepository categoryRepository;

    @Inject
    ProductRepository productRepository;

    @Inject
    GridFsTemplate gridFsTemplate;

    private final Logger log = LoggerFactory.getLogger(InventoryServiceImpl.class);

    public synchronized List<Product> importProduct(String category_id, int quantity) throws Exception {
        Category category=categoryRepository.findOne(category_id);
        if(category==null)
            throw new Exception("Category Not Found!");

        List<Product> prods=new ArrayList<Product>();
        for(int i=0;i<quantity;i++){
            Product p=new Product();
            p.setSku(autoIncrementer.getNextSequence("Product"));
            p.setCategory(category.getId());
            p.setHeight(category.getHeight());
            p.setName(category.getName());
            p.setWidth(category.getWidth());
            p.setUnit_price(category.getUnit_price());
            p.calculatePrice();
            log.debug("==============price = "+p.getPrice());
            productRepository.save(p);
            prods.add(p);
         }
        category.setProduct_quantity(category.getProduct_quantity()+quantity);
        categoryRepository.save(category);
        return prods;

    }

    @Override
    public void uploadImage(MultipartFile file, String category_id) throws IOException {

        DBObject dbo= new BasicDBObject();
        dbo.put("collection","category");
        dbo.put("id",category_id);
        Category cat=categoryRepository.findOne(category_id);
        String curid=cat.getImage_id();
        if(curid!=null&&!curid.trim().equals("")){
            gridFsTemplate.delete(new Query(Criteria.where("_id").is(cat.getImage_id())));
        }


        String fileid=gridFsTemplate.store(file.getInputStream(), file.getName(), file.getContentType(),dbo ).getId().toString();
        cat.setImage_id(fileid);
        categoryRepository.save(cat);
    }

    @Override
    public void deleteProduct(String id) throws Exception {
        Product p=productRepository.findOne(id);
        if(p==null){
            throw new Exception("Product not found");
        }
        String catid=p.getCategory();
        productRepository.delete(id);

        Category cat=categoryRepository.findOne(catid);
        if(cat==null)
            return;
        cat.reduceProduct_quantity(1);
        categoryRepository.save(cat);
    }




}
