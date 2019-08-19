package com.learnspring.SecurityRest.controllers;

import com.learnspring.SecurityRest.models.Product;
import com.learnspring.SecurityRest.repositories.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@Slf4j
public class ProductController {

    @Autowired
    ProductRepository productRepository;

    @RequestMapping(method=RequestMethod.GET, value="/api/products")
    public Iterable<Product> product() {
        log.info("Endpoint: GET /api/products");
        return productRepository.findAll();
    }

    @RequestMapping(method= RequestMethod.POST, value="/api/products")
    public String save(@RequestBody Product product) {
        log.info("Endpoint: POST /api/products");
        productRepository.save(product);
        return product.getId();
    }

    @RequestMapping(method=RequestMethod.GET, value="/api/products/{id}")
    public Optional<Product> show(@PathVariable String id) {
        log.info("Endpoint: GET /api/products/{id}");
        return productRepository.findById(id);
    }

    @RequestMapping(method=RequestMethod.PUT, value="/api/products/{id}")
    public Product update(@PathVariable String id, @RequestBody Product product) {
        log.info("Endpoint: PUT /api/products/{id}");
        Optional<Product> prod = productRepository.findById(id);
        if(product.getProdName() != null)
            prod.get().setProdName(product.getProdName());
        if(product.getProdDesc() != null)
            prod.get().setProdDesc(product.getProdDesc());
        if(product.getProdPrice() != null)
            prod.get().setProdPrice(product.getProdPrice());
        if(product.getProdImage() != null)
            prod.get().setProdImage(product.getProdImage());
        productRepository.save(prod.get());
        return prod.get();
    }

    @RequestMapping(method=RequestMethod.DELETE, value="/api/products/{id}")
    public String delete(@PathVariable String id) {
        log.info("Endpoint: DELETE /api/products/{id}");
        Optional<Product> product = productRepository.findById(id);
        productRepository.delete(product.get());
        return "product deleted";
    }
}
