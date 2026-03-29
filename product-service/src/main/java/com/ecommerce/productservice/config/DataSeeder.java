package com.ecommerce.productservice.config;

import com.ecommerce.productservice.model.Product;
import com.ecommerce.productservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataSeeder implements CommandLineRunner {

    private final ProductRepository productRepository;

    @Override
    public void run(String... args) {
        if (productRepository.count() == 0) {
            log.info("Seeding product data...");

            List<Product> products = List.of(
                new Product(null, "Apple iPhone 15 Pro", "Latest Apple smartphone with A17 Pro chip",
                        new BigDecimal("129999.99"), "Electronics", 50,
                        "https://example.com/iphone15pro.jpg", true),

                new Product(null, "Samsung Galaxy S24 Ultra", "Samsung flagship with AI features",
                        new BigDecimal("109999.99"), "Electronics", 35,
                        "https://example.com/galaxys24ultra.jpg", true),

                new Product(null, "Sony WH-1000XM5", "Industry leading noise cancelling headphones",
                        new BigDecimal("34999.99"), "Audio", 100,
                        "https://example.com/sonyxm5.jpg", true)
            );

            productRepository.saveAll(products);
            log.info("Product data seeded successfully! {} products added.", products.size());
        } else {
            log.info("Products already exist in database. Skipping seed.");
        }
    }
}
