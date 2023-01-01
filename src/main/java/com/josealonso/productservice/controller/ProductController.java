package com.josealonso.productservice.controller;

import com.josealonso.productservice.dto.ProductRequest;
import com.josealonso.productservice.dto.ProductResponse;
import com.josealonso.productservice.service.ProductService;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Validated
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
    public ResponseEntity<ProductResponse> getProductById(@NotNull @PathVariable("productId") UUID productId) {
        return new ResponseEntity<>(productService.getProductById(productId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<HttpHeaders> createProduct(@Valid @NotNull @RequestBody ProductResponse productResponse) {
        ProductResponse savedProduct = productService.createProduct(productResponse);

        HttpHeaders headers = new HttpHeaders();
        // TODO add hostname to url
        headers.add("Location", "/api/v1/product" + savedProduct.getId());

        return new ResponseEntity<HttpHeaders>(headers, HttpStatus.CREATED);
    }

    @PutMapping("/{productId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateProduct(@PathVariable("productId") UUID productId, @Valid @RequestBody ProductRequest productRequest) {
        productService.updateProductById(productId, productRequest);
    }

    @DeleteMapping("/{productId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable("productId") UUID productId) {
        productService.deleteProductById(productId);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<List<String>> validationErrorHandler(ConstraintViolationException e) {
        List<String> errors = new ArrayList<>(e.getConstraintViolations().size());

        e.getConstraintViolations().forEach(constraintViolation -> {
            errors.add(constraintViolation.getPropertyPath() + " : " + constraintViolation.getMessage());
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

}




