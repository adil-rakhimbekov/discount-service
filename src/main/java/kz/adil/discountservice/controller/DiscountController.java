package kz.adil.discountservice.controller;

import kz.adil.discountservice.model.dto.BillRequest;
import kz.adil.discountservice.model.dto.CalculationResponse;
import kz.adil.discountservice.service.BillingService;
import kz.adil.discountservice.service.ValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="/api/v1")
@RequiredArgsConstructor
public class DiscountController {
    private final BillingService discountService;
    private final ValidationService validationService;

    @PostMapping("/calculate")
    public CalculationResponse calculate(@RequestBody BillRequest billRequest){
        validationService.validateRequest(billRequest);
        return discountService.calculateBill(billRequest);
    }
}
