package kz.adil.discountservice.model.bill;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
public class ProductType {
    public static final String GROCERY = "grocery";

    @NotEmpty
    private String name;
}
