package com.josealonso.productservice.bootstrap;

import com.josealonso.productservice.domain.Product;
import com.josealonso.productservice.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class ProductLoader implements CommandLineRunner {

    public static final String PRODUCT_1_UPC = "063101000136";
    public static final String PRODUCT_2_UPC = "063101000138";
    public static final String PRODUCT_3_UPC = "008101000134";
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
                    .upc(PRODUCT_1_UPC)
                    .price(new BigDecimal(12.95))
                    .build());

            productRepository.save(Product.builder()
                    .productName("Product two")
                    .productStyle("IPB")
                    .quantity(200)
                    .upc(PRODUCT_2_UPC)
                    .price(new BigDecimal(11.95))
                    .build());

            productRepository.save(Product.builder()
                    .productName("Product three")
                    .productStyle("IPB")
                    .quantity(200)
                    .upc(PRODUCT_3_UPC)
                    .price(new BigDecimal(14.95))
                    .build());
        }

        System.out.println("BOOTSTRAP - Loaded " + productRepository.count() + " products");  // + productRepository.count());

    }
}
