package com.josealonso.productservice.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class ProductPagedList extends PageImpl<ProductResponse> {

    public ProductPagedList(List<ProductResponse> content, Pageable pageable, long total) {
        super(content, pageable, total);
    }

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public ProductPagedList(@JsonProperty("content") List<ProductResponse> content,
                            @JsonProperty("number") int number,
                            @JsonProperty("size") int size,
                            @JsonProperty("totalElements") int totalElements,
                            @JsonProperty("pageable") JsonNode pageable,
                            @JsonProperty("last") boolean last,
                            @JsonProperty("totalPages") int totalPages) {

        super(content, PageRequest.of(number, size), totalElements);
    }

    public ProductPagedList(List<ProductResponse> content) {
        super(content);
    }
}
