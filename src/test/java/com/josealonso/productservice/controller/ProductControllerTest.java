package com.josealonso.productservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.josealonso.productservice.dto.ProductRequest;
import com.josealonso.productservice.dto.ProductResponse;
import com.josealonso.productservice.service.ProductService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    final String ENDPOINT = "/api/v1/product";
    static final String PRODUCT_ID = "My Product";
    static ProductResponse productResponse;
    @Autowired
    MockMvc mockMvc;

    @MockBean
    ProductService productService;

    @Autowired
    ObjectMapper objectMapper;

    @BeforeAll
    static void setup() {
        productResponse = ProductResponse.builder()
                .id(PRODUCT_ID).name("pencil").price(BigDecimal.valueOf(200))
                .build();
    }

    @Test
    void getAllProducts() throws Exception {
        Mockito.when(productService.getAllProducts()).thenReturn(List.of(productResponse));

        mockMvc.perform(get(ENDPOINT + "/products")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void getProductById() throws Exception {
        Mockito.when(productService.getProductById(PRODUCT_ID)).thenReturn(productResponse);

        mockMvc.perform(get(ENDPOINT + "/" + PRODUCT_ID)  // UUID.randomUUID().toString())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void createProduct() throws Exception {
        ProductResponse newProduct = ProductResponse.builder().build();
        String newProductInJson = objectMapper.writeValueAsString(newProduct);
        Mockito.when(productService.createProduct(newProduct)).thenReturn(newProduct);

        mockMvc.perform(post(ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newProductInJson))
                .andExpect(status().isCreated());
    }

    @Test
    void updateProductById() throws Exception {
        ProductResponse updatedProduct = ProductResponse.builder().id("ONE").build();
        String updatedProductInJson = objectMapper.writeValueAsString(updatedProduct);
        /*Mockito.when(productService.updateProduct(updatedProduct.getId(), convertToProductRequest(updatedProduct))
                .thenReturn();*/

        mockMvc.perform(put(ENDPOINT + "/" + PRODUCT_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedProductInJson))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteProduct() throws Exception {
        ProductRequest productToBeDeleted = ProductRequest.builder().id("ONE").build();
        String productToBeDeletedInJson = objectMapper.writeValueAsString(productToBeDeleted);

        mockMvc.perform(delete(ENDPOINT + "/" + PRODUCT_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productToBeDeletedInJson))
                .andExpect(status().isNoContent());
    }

    ProductRequest convertToProductRequest(ProductResponse productResponse) {
        return ProductRequest.builder()
                .name(productResponse.getName())
                .description(productResponse.getDescription())
                .price(productResponse.getPrice())
                .build();
    }
}