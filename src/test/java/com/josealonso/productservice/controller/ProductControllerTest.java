package com.josealonso.productservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.josealonso.productservice.domain.Product;
import com.josealonso.productservice.dto.ProductRequest;
import com.josealonso.productservice.dto.ProductResponse;
import com.josealonso.productservice.repository.ProductRepository;
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
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(RestDocumentationExtension.class)
@AutoConfigureRestDocs
@WebMvcTest(ProductController.class)
@ComponentScan(basePackages = "com.josealonso.productservice.mappers")
class ProductControllerTest {

    static final UUID PRODUCT_ID = UUID.randomUUID();
    static ProductResponse productResponse;
    final String ENDPOINT = "/api/v1/product";
    @Autowired
    MockMvc mockMvc;

    @MockBean
    ProductService productService;
    @MockBean
    ProductRepository productRepository;

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
        given(productRepository.findAll()).willReturn(List.of(getValidProduct()));

        mockMvc.perform(get(ENDPOINT + "/products")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void getProductById() throws Exception {
        // Mockito.when(productService.getProductById(PRODUCT_ID)).thenReturn(productResponse);
        given(productRepository.findById(any())).willReturn(Optional.of(getValidProduct()));

        mockMvc.perform(get(ENDPOINT + "/{productId}").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("/v1/product", pathParameters(
                        parameterWithName("productId").description("UUID of the desired product to get.")
                )));
    }

    @Test
    void createProduct() throws Exception {
        ProductResponse newProduct = getValidProductResponse();
        String newProductInJson = objectMapper.writeValueAsString(newProduct);
//        Mockito.when(productService.createProduct(newProduct)).thenReturn(newProduct);

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

    Product getValidProduct() {
        return Product.builder()
                .id(PRODUCT_ID)
                //.style("Book")
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