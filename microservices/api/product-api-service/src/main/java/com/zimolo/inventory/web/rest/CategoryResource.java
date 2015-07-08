package com.zimolo.inventory.web.rest;

import com.mongodb.gridfs.GridFSDBFile;
import com.zimolo.inventory.domain.Category;
import com.zimolo.inventory.domain.Product;
import com.zimolo.inventory.repository.CategoryRepository;
import com.zimolo.inventory.service.InventoryService;
import com.zimolo.inventory.web.dto.CategoryImportDTO;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.inject.Inject;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Category.
 */
@RestController
public class CategoryResource {

    private final Logger log = LoggerFactory.getLogger(CategoryResource.class);

    @Inject
    private CategoryRepository categoryRepository;

    @Inject
    private InventoryService inventoryService;

    @Inject
    GridFsTemplate gridFsTemplate;


    /**
     * POST  /categorys/:id/import/quantity/:quantity-> import products under "id" category, and the quantity is "quantity".
     */
    @RequestMapping(value = "/categorys/import",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Product> import_products(@RequestBody CategoryImportDTO dto) {
        log.debug("REST request to import products with quantity Category : {}", dto.getCategory_id());
        List<Product> ret=null;
        try {
            ret=inventoryService.importProduct(dto.getCategory_id(), dto.getImport_quantity());
        }catch (Exception e){
            log.error(e.getMessage());
            return null;
        }
        return ret;
    }


    /**
     * POST  /categorys -> Create a new category.
     */
    @RequestMapping(value = "/categorys",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Category> create(@RequestBody Category category) throws URISyntaxException {
        log.debug("REST request to save Category : {}", category);
        if (category.getId() != null) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        categoryRepository.save(category);
        return new ResponseEntity<>(category,HttpStatus.OK);
    }

    /**
     * PUT  /categorys -> Updates an existing category.
     */
    @RequestMapping(value = "/categorys",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Category> update(@RequestBody Category category) throws URISyntaxException {
        log.debug("REST request to update Category : {}", category);
        if (category.getId() == null) {
            return create(category);
        }
        categoryRepository.save(category);
        return new ResponseEntity<>(category,HttpStatus.OK);
    }

    /**
     * GET  /categorys -> get all the categorys.
     */
    @RequestMapping(value = "/categorys",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Category> getAll() {
        log.debug("REST request to get all Categorys");
        return categoryRepository.findAll();
    }

    /**
     * GET  /categorys/:id -> get the "id" category.
     */
    @RequestMapping(value = "/categorys/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Category> get(@PathVariable String id) {
        log.debug("REST request to get Category : {}", id);
        return Optional.ofNullable(categoryRepository.findOne(id))
            .map(category -> new ResponseEntity<>(
                category,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /categorys/:id -> delete the "id" category.
     */
    @RequestMapping(value = "/categorys/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public void delete(@PathVariable String id) {
        log.debug("REST request to delete Category : {}", id);
        categoryRepository.delete(id);
    }

    /**
     * upload a image
     */
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/categorys/image/upload",
                    method = RequestMethod.POST)
    public void upload(@RequestParam("file") MultipartFile file, @RequestParam("category_id") String category_id ) throws IOException {
        inventoryService.uploadImage(file, category_id);
    }



    /**
     * get a image
     */
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/imgget/categorys/{id}/image/{timestamp}",
                    method = RequestMethod.GET,
                    produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<byte[]> getimage(@PathVariable String id, @PathVariable String timestamp) throws IOException {
        Category cat=categoryRepository.findOne(id);
        GridFSDBFile gridfile = this.gridFsTemplate.findOne(new Query(Criteria.where("_id").is(cat.getImage_id())));
        InputStream in= gridfile.getInputStream();
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(gridfile.getContentType()));
        return new ResponseEntity<byte[]>(IOUtils.toByteArray(in), headers, HttpStatus.OK);
    }

}


