package kz.adil.discountservice.service.impl;

import kz.adil.discountservice.model.Bill;
import kz.adil.discountservice.model.User;
import kz.adil.discountservice.model.dto.BillRequest;
import kz.adil.discountservice.model.dto.CalculationResponse;
import kz.adil.discountservice.model.dto.DiscountInfo;
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
        return Bill.builder()
                .customer(currentUser)
                .items(billRequest.getItems())
                .build();
    }

    private CalculationResponse prepareResult(Bill bill, DiscountInfo discountInfo) {
        final BigDecimal discountAmount = discountInfo.getDiscountAmount();
        final BigDecimal totalAmount = bill.originalAmount();
        final BigDecimal netAmount = totalAmount.subtract(discountAmount);
        return CalculationResponse.builder()
                .discountAmount(String.valueOf(discountAmount))
                .createdDatetime(ZonedDateTime.now())
                .totalAmount(String.valueOf(totalAmount))
                .netAmount(String.valueOf(netAmount))
                .build();
    }
}
