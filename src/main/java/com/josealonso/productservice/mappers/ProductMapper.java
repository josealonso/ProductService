package com.josealonso.productservice.mappers;

import com.josealonso.productservice.domain.Product;
import com.josealonso.productservice.dto.ProductDto;
import org.mapstruct.Mapper;

@Mapper(uses = {DateMapper.class})
public interface ProductMapper {
    ProductDto productToProductDto(Product product);

    Product productDtoToProduct(ProductDto productDto);
}
