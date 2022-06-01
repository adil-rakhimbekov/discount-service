package kz.adil.discountservice.model.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class DiscountInfo {
    private BigDecimal discountAmount;
}
