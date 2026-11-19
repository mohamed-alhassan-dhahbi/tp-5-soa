package com.iset.billing_service.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.iset.billing_service.entities.Product;

@FeignClient(name = "INVENTORY-SERVICE")
public interface InventoryService {
    @GetMapping("/products/{id}")
    Product findProductById(@PathVariable("id") Long id);

    @GetMapping("/products")
    PagedModel<Product> findAllProducts();
}