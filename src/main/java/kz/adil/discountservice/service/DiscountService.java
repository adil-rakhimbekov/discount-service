package kz.adil.discountservice.service;

import kz.adil.discountservice.model.Bill;
import kz.adil.discountservice.model.dto.DiscountInfo;

public interface DiscountService {
    DiscountInfo calculateDiscount(Bill bill);
}
