package kz.adil.discountservice.model.bill;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductType {
    public static final String GROCERY = "grocery";
    private String name;
}
