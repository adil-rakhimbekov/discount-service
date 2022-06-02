package kz.adil.discountservice.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;

import kz.adil.discountservice.model.bill.Bill;
import kz.adil.discountservice.model.user.User;
import kz.adil.discountservice.dto.BillRequest;
import kz.adil.discountservice.dto.CalculationResponse;
import kz.adil.discountservice.dto.DiscountInfo;
import kz.adil.discountservice.service.DiscountService;
import kz.adil.discountservice.service.UserRetrievingService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {BillingServiceImpl.class})
@ExtendWith(SpringExtension.class)
class BillingServiceImplTest {
    @Autowired
    private BillingServiceImpl billingServiceImpl;

    @MockBean
    private DiscountService discountService;

    @MockBean
    private UserRetrievingService userRetrievingService;

    /**
     * Method under test: {@link BillingServiceImpl#calculateBill(BillRequest)}
     */
    @Test
    void testCalculateBill() {
        // Arrange
        when(this.userRetrievingService.getCurrentUser()).thenReturn(mock(User.class));

        DiscountInfo discountInfo = new DiscountInfo();
        discountInfo.setDiscountAmount(BigDecimal.valueOf(42L));
        when(this.discountService.calculateDiscount(any())).thenReturn(discountInfo);

        BillRequest billRequest = new BillRequest();
        billRequest.setItems(new ArrayList<>());

        // Act
        CalculationResponse actualCalculateBillResult = this.billingServiceImpl.calculateBill(billRequest);

        // Assert
        assertEquals("0", actualCalculateBillResult.getTotalAmount());
        assertEquals("42", actualCalculateBillResult.getDiscountAmount());
        assertEquals("-42", actualCalculateBillResult.getNetAmount());
        verify(this.userRetrievingService).getCurrentUser();
        verify(this.discountService).calculateDiscount(any());
    }
}

