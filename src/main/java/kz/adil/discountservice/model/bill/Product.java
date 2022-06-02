package kz.adil.discountservice.model.bill;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class Product {
    private String name;
    private ProductType type;
    private BigDecimal price;
}
