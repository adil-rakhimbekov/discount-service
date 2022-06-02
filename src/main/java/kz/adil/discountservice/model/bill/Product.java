package kz.adil.discountservice.model.bill;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Getter
@Setter
public class Product {
    @NotEmpty
    private String name;
    @NotNull
    private ProductType type;
    @Positive
    private BigDecimal price;
}
