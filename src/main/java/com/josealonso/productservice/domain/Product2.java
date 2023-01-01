package com.josealonso.productservice.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Product2 {

    private UUID id;
    private Long version;
    private String productName;
    private String productStyle;
    private Long upc;
    private BigDecimal price;
    private Integer quantity;
}
