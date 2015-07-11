package com.zimolo.inventory.service;


import com.zimolo.inventory.domain.CheckedOutProduct;

public interface InventoryCheckoutService {


    CheckedOutProduct checkout_by_sku(String sku) throws Exception;

}
