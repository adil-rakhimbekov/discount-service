package kz.adil.discountservice.service.impl;

import kz.adil.discountservice.dto.BillRequest;
import kz.adil.discountservice.dto.CalculationResponse;
import kz.adil.discountservice.dto.DiscountInfo;
import kz.adil.discountservice.model.bill.Bill;
import kz.adil.discountservice.model.user.User;
import kz.adil.discountservice.service.BillingService;
import kz.adil.discountservice.service.DiscountService;
import kz.adil.discountservice.service.UserRetrievingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class BillingServiceImpl implements BillingService {

    private final DiscountService discountService;
    private final UserRetrievingService userRetrievingService;

    @Override
    public CalculationResponse calculateBill(BillRequest billRequest) {
        final Bill bill = convertToBill(billRequest);
        final DiscountInfo discountInfo = discountService.calculateDiscount(bill);
        return prepareResult(bill, discountInfo);
    }

    private Bill convertToBill(BillRequest billRequest) {
        final User currentUser = userRetrievingService.getCurrentUser();
        return new Bill(currentUser, billRequest.getItems());
    }

    private CalculationResponse prepareResult(Bill bill, DiscountInfo discountInfo) {
        final BigDecimal discountAmount = discountInfo.getDiscountAmount();
        final BigDecimal totalAmount = bill.originalAmount();
        final BigDecimal netAmount = totalAmount.subtract(discountAmount);
        return new CalculationResponse(ZonedDateTime.now(), String.valueOf(discountAmount), String.valueOf(totalAmount), String.valueOf(netAmount));
    }
}
