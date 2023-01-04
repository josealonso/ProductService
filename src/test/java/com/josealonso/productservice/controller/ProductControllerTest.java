package com.josealonso.productservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.josealonso.productservice.bootstrap.ProductLoader;
import com.josealonso.productservice.dto.ProductDto;
import com.josealonso.productservice.dto.ProductRequest;
import com.josealonso.productservice.dto.ProductResponse;
import com.josealonso.productservice.repository.ProductRepository;
import com.josealonso.productservice.service.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.constraints.ConstraintDescriptions;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(RestDocumentationExtension.class)
@AutoConfigureRestDocs(uriScheme = "https", uriHost = "com.josealonso", uriPort = 80)
@WebMvcTest(ProductController.class)
@ComponentScan(basePackages = "com.josealonso.productservice.mappers")
class ProductControllerTest {

    static final String PRODUCT_ID = "a15d1a05-ff1a-4cc9-ac95-864fd820e530";
    final String ENDPOINT = "/api/v1/product";
    @Autowired
    MockMvc mockMvc;

    @MockBean
    ProductService productService;
    @MockBean
    ProductRepository productRepository;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void getAllProducts() throws Exception {
        given(productService.getAllProducts()).willReturn(List.of(getValidProductDto()));

        mockMvc.perform(get(ENDPOINT + "/products")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void getProductById() throws Exception {
        given(productService.getProductById(any())).willReturn(getValidProductDto());

        mockMvc.perform(get(ENDPOINT + "/{productId}", PRODUCT_ID)
                        .param("isInDiscount", "yes")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("v1/product-get",
                        pathParameters(
                                parameterWithName("productId").description("UUID of the desired product to get.")
                        ),
                        queryParameters(
                                parameterWithName("isInDiscount").description("Is in discount query param")
                        ),
                        responseFields(
                                fieldWithPath("id").description("Id of Product"),
                                fieldWithPath("version").description("Version number"),
                                fieldWithPath("createdDate").description("Date Created"),
                                fieldWithPath("lastModifiedDate").description("Date Updated"),
                                fieldWithPath("name").description("Product Name"),
                                fieldWithPath("style").description("Product Style"),
                                fieldWithPath("upc").description("UPC of Product"),
                                fieldWithPath("price").description("Price"),
                                fieldWithPath("quantity").description("Quantity")
                        )));
    }

    @Test
    void createProduct() throws Exception {
        ProductDto newProduct = getValidProductDto();
        String newProductInJson = objectMapper.writeValueAsString(newProduct);

        given(productService.createProduct(any())).willReturn(getValidProductDto());

        ConstrainedFields fields = new ConstrainedFields(ProductResponse.class);

        mockMvc.perform(post(ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newProductInJson))
                .andExpect(status().isCreated())
                .andDo(document("v1/product-new",
                        requestFields(
                                fields.withPath("id").ignored(),
                                fields.withPath("version").ignored(),
                                fields.withPath("createdDate").ignored(),
                                fields.withPath("lastModifiedDate").ignored(),
                                fields.withPath("name").description("Product Name"),
                                fields.withPath("style").description("Product Style"),
                                fields.withPath("upc").description("UPC of Product").attributes(),
                                fields.withPath("price").description("Price"),
                                fields.withPath("quantity").description("Quantity")
                        )));
    }

    @Test
    void updateProductById() throws Exception {
        ProductDto updatedProduct = getValidProductDto();
        String updatedProductInJson = objectMapper.writeValueAsString(updatedProduct);

        given(productService.updateProductById(any(), any())).willReturn(getValidProductDto());

        mockMvc.perform(put(ENDPOINT + "/" + PRODUCT_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedProductInJson))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteProduct() throws Exception {
        ProductRequest productToBeDeleted = ProductRequest.builder().id(UUID.fromString(PRODUCT_ID)).build();
        String productToBeDeletedInJson = objectMapper.writeValueAsString(productToBeDeleted);

        mockMvc.perform(delete(ENDPOINT + "/" + PRODUCT_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productToBeDeletedInJson))
                .andExpect(status().isNoContent());
    }

    ProductDto getValidProductDto() {
        return ProductDto.builder()
                .id(UUID.fromString(PRODUCT_ID))
                .createdDate(OffsetDateTime.of(LocalDate.now(), LocalTime.NOON, ZoneOffset.UTC))
                .lastModifiedDate(OffsetDateTime.of(LocalDate.now(), LocalTime.NOON, ZoneOffset.UTC))
                .version(1)
                .name("My product")
                .style("Book")
                .price(new BigDecimal("2.99"))
                .upc(ProductLoader.PRODUCT_1_UPC)
                .quantity(23)
                .build();
    }


    private static class ConstrainedFields {

        private final ConstraintDescriptions constraintDescriptions;

        public ConstrainedFields(Class<?> input) {
            this.constraintDescriptions = new ConstraintDescriptions(input);
        }

        private FieldDescriptor withPath(String path) {
            return fieldWithPath(path).attributes(key("constraints").value(StringUtils
                    .collectionToDelimitedString(this.constraintDescriptions
                            .descriptionsForProperty(path), ", ")));
        }
    }
}

