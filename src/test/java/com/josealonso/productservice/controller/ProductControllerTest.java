package com.josealonso.productservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.josealonso.productservice.dto.ProductRequest;
import com.josealonso.productservice.dto.ProductResponse;
import com.josealonso.productservice.service.ProductService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(RestDocumentationExtension.class)
@AutoConfigureRestDocs
@WebMvcTest(ProductController.class)
@ComponentScan(basePackages = "com.josealonso.productservice.mappers")
class ProductControllerTest {

    final String ENDPOINT = "/api/v1/product";
    static final UUID PRODUCT_ID = UUID.randomUUID();
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

        mockMvc.perform(get(ENDPOINT + "/" + PRODUCT_ID)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void createProduct() throws Exception {
        ProductResponse newProduct = getValidProductResponse();
        String newProductInJson = objectMapper.writeValueAsString(newProduct);
        Mockito.when(productService.createProduct(newProduct)).thenReturn(newProduct);

        mockMvc.perform(post(ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newProductInJson))
                .andExpect(status().isCreated());
    }

    @Test
    void updateProductById() throws Exception {
        ProductResponse updatedProduct = getValidProductResponse();
        String updatedProductInJson = objectMapper.writeValueAsString(updatedProduct);
        /*Mockito.when(productService.updateProductById(updatedProduct.getId(), convertToProductRequest(updatedProduct))
                .thenReturn();*/

        mockMvc.perform(put(ENDPOINT + "/" + PRODUCT_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedProductInJson))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteProduct() throws Exception {
        ProductRequest productToBeDeleted = ProductRequest.builder().id(PRODUCT_ID).build();
        String productToBeDeletedInJson = objectMapper.writeValueAsString(productToBeDeleted);

        mockMvc.perform(delete(ENDPOINT + "/" + PRODUCT_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productToBeDeletedInJson))
                .andExpect(status().isNoContent());
    }

    ProductResponse getValidProductResponse() {
        return ProductResponse.builder()
                .name("My product")
                .style("Book")
                .price(new BigDecimal("2.99"))
                .upc(123123123L)
                .build();
    }

    ProductRequest getValidProductRequest() {
        return ProductRequest.builder()
                .name("My product")
                .style("Book")
                .price(new BigDecimal("2.99"))
                .upc(123123123L)
                .build();
    }
    ProductRequest convertToProductRequest(ProductResponse productResponse) {
        return ProductRequest.builder()
                .name(productResponse.getName())
                .style(productResponse.getStyle())
                .price(productResponse.getPrice())
                .build();
    }
}