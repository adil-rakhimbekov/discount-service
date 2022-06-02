package kz.adil.discountservice.controller;

import kz.adil.discountservice.dto.BillRequest;
import kz.adil.discountservice.dto.CalculationResponse;
import kz.adil.discountservice.service.BillingService;
import kz.adil.discountservice.service.ValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(path="/api/v1/discount")
@RequiredArgsConstructor
public class DiscountController {
    private final BillingService discountService;
    private final ValidationService validationService;

    @PostMapping("/calculate")
    public CalculationResponse calculate(@Valid @RequestBody BillRequest billRequest){
        validationService.validateRequest(billRequest);
        return discountService.calculateBill(billRequest);
    }
}
