package com.josealonso.productservice.mappers;

import com.josealonso.productservice.domain.Product;
import com.josealonso.productservice.dto.ProductRequest;
import org.mapstruct.Mapper;

@Mapper(uses = {DateMapper.class})
public interface ProductMapper {
    ProductRequest productToProductRequest(Product product);

    Product productRequestToProduct(ProductRequest productRequest);
}
