package com.josealonso.productservice.mappers;

import com.josealonso.productservice.domain.Product2;
import com.josealonso.productservice.dto.ProductRequest;
import org.mapstruct.Mapper;

@Mapper
public interface Product2Mapper {
    ProductRequest product2ToProductRequest(Product2 product2);

    Product2 productRequestToProduct2(ProductRequest productRequest);
}
