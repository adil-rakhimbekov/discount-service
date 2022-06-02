package kz.adil.discountservice.service;

import kz.adil.discountservice.model.bill.Bill;
import kz.adil.discountservice.dto.DiscountInfo;

public interface DiscountService {
    DiscountInfo calculateDiscount(Bill bill);
}
