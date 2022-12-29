package com.josealonso.productservice.dto;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class ProductPagedList extends PageImpl<ProductResponse> {

    public ProductPagedList(List<ProductResponse> content, Pageable pageable, long total) {
        super(content, pageable, total);
    }

    public ProductPagedList(List<ProductResponse> content) {
        super(content);
    }
}
