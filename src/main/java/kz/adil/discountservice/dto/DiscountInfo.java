package kz.adil.discountservice.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class DiscountInfo {
    private BigDecimal discountAmount;
}
