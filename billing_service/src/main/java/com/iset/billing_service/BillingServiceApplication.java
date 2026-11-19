package com.iset.billing_service;

import java.util.Date;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.hateoas.PagedModel;

import com.iset.billing_service.entities.Bill;
import com.iset.billing_service.entities.Customer;
import com.iset.billing_service.entities.Product;
import com.iset.billing_service.entities.ProductItem;
import com.iset.billing_service.repository.BillRepository;
import com.iset.billing_service.repository.ProductItemRepository;
import com.iset.billing_service.service.CustomerService;
import com.iset.billing_service.service.InventoryService;

@SpringBootApplication
@EnableFeignClients
public class BillingServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(BillingServiceApplication.class, args);
	}

	@Bean
    CommandLineRunner start(BillRepository billRepository,
                            ProductItemRepository productItemRepository,
                            CustomerService customerService,
                            InventoryService inventoryService) {
        return args -> {
            Customer c1 = customerService.findCustomerById(1L);
            System.out.println("ID= " + c1.getId());
            System.out.println("Name= " + c1.getName());
            System.out.println("Email= " + c1.getEmail());

            Bill bill1 = billRepository.save(new Bill(null, new Date(), c1.getId(), null, null));

            PagedModel<Product> products = inventoryService.findAllProducts();
            products.getContent().forEach(p ->
                productItemRepository.save(new ProductItem(null, p.getId(), null, p.getPrice(), 90, bill1)));
        };
    }
}
