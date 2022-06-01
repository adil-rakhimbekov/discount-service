package kz.adil.discountservice.model.discount;

import kz.adil.discountservice.model.Bill;

import java.math.BigDecimal;

@FunctionalInterface
public interface Discount {
    BigDecimal apply(Bill bill);
}
