package com.josealonso.productservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponse {
    private Integer apiVersion;
    private String id;
    private String name;
    private String description;
    private BigDecimal price;

    private OffsetDateTime createdDate;
    private OffsetDateTime lastModifiedDate;
}
