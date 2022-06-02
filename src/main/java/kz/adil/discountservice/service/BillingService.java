package kz.adil.discountservice.service;

import kz.adil.discountservice.dto.BillRequest;
import kz.adil.discountservice.dto.CalculationResponse;

public interface BillingService {
    CalculationResponse calculateBill(BillRequest billRequest);
}
