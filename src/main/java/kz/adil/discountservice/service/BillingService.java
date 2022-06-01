package kz.adil.discountservice.service;

import kz.adil.discountservice.model.dto.BillRequest;
import kz.adil.discountservice.model.dto.CalculationResponse;

public interface BillingService {
    CalculationResponse calculateBill(BillRequest billRequest);
}
