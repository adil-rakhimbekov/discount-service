package kz.adil.discountservice.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Product {
    private String name;
    private ProductType type;
    private BigDecimal price;
}
