package kz.adil.discountservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import kz.adil.discountservice.dto.BillRequest;
import kz.adil.discountservice.dto.CalculationResponse;
import kz.adil.discountservice.model.bill.Item;
import kz.adil.discountservice.service.BillingService;
import kz.adil.discountservice.service.ValidationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.ZonedDateTime;
import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {DiscountController.class})
@ExtendWith(SpringExtension.class)
class DiscountControllerTest {
    @MockBean
    private BillingService billingService;

    @Autowired
    private DiscountController discountController;

    @MockBean
    private ValidationService validationService;

    /**
     * Method under test: {@link DiscountController#calculate(BillRequest)}
     */
    @Test
    void testCalculate() {
        // Arrange
        BillRequest billRequest = new BillRequest();
        ArrayList<Item> items = new ArrayList<>();
        billRequest.setItems(items);

        ObjectMapper objectMapper = new ObjectMapper();
        final CalculationResponse calculationResponse = new CalculationResponse(ZonedDateTime.now(), "10",
                "100", "90");
        when(billingService.calculateBill(billRequest)).thenReturn(calculationResponse);

        // Act
        final CalculationResponse calculate = discountController.calculate(billRequest);

        // Assert
        verify(billingService).calculateBill(any());
        verify(validationService).validateRequest(any());
    }
}

