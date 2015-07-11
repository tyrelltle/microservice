package com.zimolo.inventory.web.rest;

import com.zimolo.inventory.domain.CheckedOutProduct;
import com.zimolo.inventory.repository.CheckedOutProductRepository;
import com.zimolo.inventory.service.InventoryCheckoutService;
import com.zimolo.inventory.web.dto.TypeDataDTO;
import com.zimolo.inventory.web.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.util.List;

/**
 * REST controller for managing Product.
 */
@RestController
public class ProductCheckoutResource {

    private final Logger log = LoggerFactory.getLogger(ProductCheckoutResource.class);

    @Autowired
    private InventoryCheckoutService inventoryCheckoutService;

    @Autowired
    private CheckedOutProductRepository checkedOutProductRepository;
    /**
     * POST  /products -> Create a new product.
     */

    @RequestMapping(value = "/product_checkout/{sku}",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TypeDataDTO> create(@PathVariable String sku) throws URISyntaxException {
        log.debug("REST request to checkout product : {}", sku);
        CheckedOutProduct p=null;
        try {
            p=inventoryCheckoutService.checkout_by_sku(sku);
        } catch (Exception e) {
             return new ResponseEntity<>(TypeDataDTO.ErrorInstance(e.getMessage()),HttpStatus.OK);
        }
        return new ResponseEntity<>(TypeDataDTO.SuccessInstance(p),HttpStatus.OK);
    }




    /**
     * GET  /products -> get all the products.
     */
    @RequestMapping(value = "/product_checkout/list",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CheckedOutProduct>> getAll(@RequestParam(value = "page" , required = false) Integer offset,
                                                @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
        Page<CheckedOutProduct> page = checkedOutProductRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/product_checkout/list", offset, limit);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }




}
