package com.josealonso.productservice.controller;

import com.josealonso.productservice.dto.ProductRequest;
import com.josealonso.productservice.dto.ProductResponse;
import com.josealonso.productservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/products")
    @ResponseStatus(HttpStatus.OK)
    public List<ProductResponse> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductResponse> getProduct(@PathVariable("productId") String productId) {
        return new ResponseEntity<>(productService.getProductById(productId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<HttpHeaders> createProduct(@RequestBody ProductResponse productResponse) {
        ProductResponse savedProduct = productService.createProduct(productResponse);

        HttpHeaders headers = new HttpHeaders();
        // TODO add hostname to url
        headers.add("Location", "/api/v1/product" + savedProduct.getId());

        return new ResponseEntity<HttpHeaders>(headers, HttpStatus.CREATED);
    }

    @PutMapping("/{productId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
        public void updateProduct(@PathVariable("productId") String productId, @RequestBody ProductRequest productRequest) {
        productService.updateProduct(productId, productRequest);
    }

    @DeleteMapping({"/productId"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable("productId") String productId) {
        productService.deleteProductById(productId);
    }

}




