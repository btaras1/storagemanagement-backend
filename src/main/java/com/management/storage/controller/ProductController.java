package com.management.storage.controller;

import com.management.storage.model.Product;
import com.management.storage.model.Product;
import com.management.storage.repository.ProductRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductRepository productRepository;

    @GetMapping
    List<Product> findAll(){ return productRepository.findAll(); }

    @GetMapping("{id}")
    public Product findById(@PathVariable Long id){return productRepository.getById(id);}

    @PostMapping
    public Product create(@RequestBody final Product product){return productRepository.saveAndFlush(product);}

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public Product update(@PathVariable Long id, @RequestBody Product product){
        Product currentProduct = productRepository.getById(id);
        BeanUtils.copyProperties(product, currentProduct, "id");
        return productRepository.saveAndFlush(currentProduct);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable Long id){
        productRepository.deleteById(id);
    }
}
