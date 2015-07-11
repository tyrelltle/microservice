package com.zimolo.inventory.service.impl;

import com.zimolo.inventory.domain.CheckedOutProduct;
import com.zimolo.inventory.domain.Product;
import com.zimolo.inventory.repository.CheckedOutProductRepository;
import com.zimolo.inventory.repository.ProductRepository;
import com.zimolo.inventory.service.InventoryCheckoutService;
import com.zimolo.inventory.service.InventoryService;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class InventoryCheckoutServiceImpl implements InventoryCheckoutService {
    @Inject
    ProductRepository productRepository;

    @Inject
    InventoryService inventoryService;

    @Inject
    CheckedOutProductRepository checkedOutProductRepository;

    @Override
    public CheckedOutProduct checkout_by_sku(String sku) throws Exception {
        Product prod=productRepository.findBySku(Integer.valueOf(sku));
        if(prod==null)
            throw new Exception("Product Not Found");
        inventoryService.deleteProduct(prod.getId());
        CheckedOutProduct checkedOutProduct=new CheckedOutProduct(prod);
        checkedOutProductRepository.save(checkedOutProduct);
        return checkedOutProduct;
    }
}
