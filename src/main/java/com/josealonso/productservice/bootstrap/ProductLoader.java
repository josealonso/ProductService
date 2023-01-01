package com.josealonso.productservice.bootstrap;

import com.josealonso.productservice.domain.Product;
import com.josealonso.productservice.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class ProductLoader implements CommandLineRunner {

    private final ProductRepository productRepository;

    public ProductLoader(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        loadProductObjects();
    }

    private void loadProductObjects() {
        if (productRepository.count() == 0) {

            productRepository.save(Product.builder()
                    .productName("Product one")
                    .productStyle("IPA")
                    .quantity(200)
                    .upc(337010001L)
                    .price(new BigDecimal(12.95))
                    .build());

            productRepository.save(Product.builder()
                    .productName("Product two")
                    .productStyle("IPB")
                    .quantity(200)
                    .upc(337010002L)
                    .price(new BigDecimal(11.95))
                    .build());
        }

        System.out.println("BOOTSTRAP - Loaded " + productRepository.count() + " products");  // + productRepository.count());

    }
}
