package com.iset.customer_service;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.config.Projection;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@EnableConfigurationProperties(ConfigParams.class)
@SpringBootApplication
public class CustomerServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CustomerServiceApplication.class, args);
	}

	@Bean
    CommandLineRunner start(CustomerRepository customerRepository,
                            RepositoryRestConfiguration restConfiguration) {
        return args -> {
            restConfiguration.exposeIdsFor(Customer.class);
            customerRepository.save(new Customer(null, "ISET", "ISET@gmail.com"));
            customerRepository.save(new Customer(null, "ENSI", "ensi@gmail.com"));
            customerRepository.save(new Customer(null, "ENIT", "enit@gmail.com"));
        };
    }
}

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String email;
}

@RepositoryRestResource
interface CustomerRepository extends JpaRepository<Customer, Long> {
    Customer findCustomerByName(String name);
}

@Projection(name = "fullCustomer", types = Customer.class)
interface CustomerProjection {
    Long getId();
    String getName();
    String getEmail();
}