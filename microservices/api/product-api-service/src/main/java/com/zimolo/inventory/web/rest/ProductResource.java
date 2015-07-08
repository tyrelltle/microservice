package com.zimolo.inventory.web.rest;

import com.zimolo.inventory.domain.Product;
import com.zimolo.inventory.repository.CategoryRepository;
import com.zimolo.inventory.repository.ProductRepository;
import com.zimolo.inventory.service.InventoryService;
import com.zimolo.inventory.service.util.AutoIncrementer;
import com.zimolo.inventory.web.dto.TypeDataDTO;
import com.zimolo.inventory.web.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Product.
 */
@RestController
public class ProductResource {

    private final Logger log = LoggerFactory.getLogger(ProductResource.class);

    @Autowired
    private MongoOperations mongo;

    @Autowired
    private CategoryRepository categoryRepository;

    @Inject
    private InventoryService inventoryService;

    @Inject
    private AutoIncrementer autoIncrementer;

    @Inject
    private ProductRepository productRepository;

    /**
     * POST  /products -> Create a new product.
     */

    @RequestMapping(value = "/products",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> create(@Valid @RequestBody Product product) throws URISyntaxException {
        log.debug("REST request to save Product : {}", product);
        if (product.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new product cannot already have an ID").build();
        }
        product.setSku(autoIncrementer.getNextSequence("Product"));
        productRepository.save(product);
        return ResponseEntity.created(new URI("/api/products/" + product.getId())).build();
    }


    /**
     * PUT  /products -> Updates an existing product.
     */
    @RequestMapping(value = "/products",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> update(@Valid @RequestBody Product product) throws URISyntaxException {
        log.debug("REST request to update Product : {}", product);
        if (product.getId() == null) {
            return create(product);
        }
        productRepository.save(product);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /products -> get all the products.
     */
    @RequestMapping(value = "/products",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Product>> getAll(@RequestParam(value = "page" , required = false) Integer offset,
                                                @RequestParam(value = "per_page", required = false) Integer limit,
                                                @RequestParam(value = "category_id", required = false) String category_id)
        throws URISyntaxException {
        Page<Product> page = null;
        if(category_id==null) {
            page = productRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
        }
        else{
            page = productRepository.findByCategory(category_id,PaginationUtil.generatePageRequest(offset, limit));
         }


        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/products", offset, limit);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /products/:id -> get the "id" product.
     */
    @RequestMapping(value = "/products/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Product> get(@PathVariable String id) {
        log.debug("REST request to get Product : {}", id);
        return Optional.ofNullable(productRepository.findOne(id))
            .map(product -> new ResponseEntity<>(
                product,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /products/:id -> delete the "id" product.
     */
    @RequestMapping(value = "/products/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TypeDataDTO> delete(@PathVariable String id) {
        log.debug("REST request to delete Product : {}", id);
        try {
            inventoryService.deleteProduct(id);
        }catch (Exception e){
             return new ResponseEntity<TypeDataDTO>(TypeDataDTO.ErrorInstance(e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(TypeDataDTO.SuccessInstance(null),HttpStatus.OK);
    }
}
