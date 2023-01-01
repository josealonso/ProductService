package com.josealonso.productservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {
    private Integer apiVersion;
    private UUID id;
    private String name;
    private String description;
    private BigDecimal price;

    private OffsetDateTime createdDate;
    private OffsetDateTime lastModifiedDate;

}
