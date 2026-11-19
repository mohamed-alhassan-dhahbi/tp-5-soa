package com.iset.billing_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.iset.billing_service.entities.Bill;
import com.iset.billing_service.repository.BillRepository;

@RestController
public class BillRestController {
    @Autowired private BillRepository billRepository;
    @Autowired private CustomerService customerService;
    @Autowired private InventoryService inventoryService;

    @GetMapping("/fullBill/{id}")
    public Bill getBill(@PathVariable Long id) {
        Bill bill = billRepository.findById(id).get();
        bill.setCustomer(customerService.findCustomerById(bill.getCustomerID()));
        bill.getProductItems().forEach(pi ->
            pi.setProduct(inventoryService.findProductById(pi.getProductID())));
        return bill;
    }
}