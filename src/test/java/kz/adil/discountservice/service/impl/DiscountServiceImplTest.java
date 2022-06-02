package kz.adil.discountservice.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;

import kz.adil.discountservice.model.bill.Bill;
import kz.adil.discountservice.model.discount.AbstractDiscount;
import kz.adil.discountservice.model.discount.BillDiscount;
import kz.adil.discountservice.dto.DiscountInfo;
import kz.adil.discountservice.repository.DiscountRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {DiscountServiceImpl.class})
@ExtendWith(SpringExtension.class)
class DiscountServiceImplTest {
    @MockBean
    private DiscountRepository discountRepository;

    @Autowired
    private DiscountServiceImpl discountServiceImpl;

    /**
     * Method under test: {@link DiscountServiceImpl#calculateDiscount(Bill)}
     */
    @Test
    void testCalculateDiscount() {
        // Arrange
        when(this.discountRepository.findAll((org.springframework.data.domain.Sort) any())).thenReturn(new ArrayList<>());
        Bill bill = mock(Bill.class);

        // Act
        DiscountInfo actualCalculateDiscountResult = this.discountServiceImpl.calculateDiscount(bill);

        // Assert
        assertEquals("0", actualCalculateDiscountResult.getDiscountAmount().toString());
        verify(this.discountRepository).findAll((org.springframework.data.domain.Sort) any());
    }

    /**
     * Method under test: {@link DiscountServiceImpl#calculateDiscount(Bill)}
     */
    @Test
    void testCalculateDiscount2() {
        // Arrange
        ArrayList<AbstractDiscount> abstractDiscountList = new ArrayList<>();
        BigDecimal percent = BigDecimal.valueOf(42L);
        BigDecimal amount = BigDecimal.valueOf(42L);
        abstractDiscountList.add(new BillDiscount("level", 2, 2, true, percent, amount, BigDecimal.valueOf(42L)));
        when(this.discountRepository.findAll((org.springframework.data.domain.Sort) any()))
                .thenReturn(abstractDiscountList);
        Bill bill = mock(Bill.class);
        when(bill.originalAmount()).thenReturn(BigDecimal.valueOf(42L));

        // Act
        final DiscountInfo discountInfo = this.discountServiceImpl.calculateDiscount(bill);

        // Assert
        assertEquals(BigDecimal.valueOf(59.64), discountInfo.getDiscountAmount());
    }

    /**
     * Method under test: {@link DiscountServiceImpl#calculateDiscount(Bill)}
     */
    @Test
    void testCalculateDiscount3() {
        // Arrange
        ArrayList<AbstractDiscount> abstractDiscountList = new ArrayList<>();
        BigDecimal percent = BigDecimal.valueOf(10L);
        BigDecimal amount = BigDecimal.valueOf(0L);
        abstractDiscountList.add(new BillDiscount("level", 2, 2, false, percent, amount, BigDecimal.valueOf(42L)));
        abstractDiscountList.add(new BillDiscount("level", 1, 1, false, percent, amount, BigDecimal.valueOf(10L)));
        when(this.discountRepository.findAll((org.springframework.data.domain.Sort) any()))
                .thenReturn(abstractDiscountList);
        Bill bill = mock(Bill.class);
        when(bill.originalAmount()).thenReturn(BigDecimal.valueOf(42L));

        // Act
        final DiscountInfo discountInfo = this.discountServiceImpl.calculateDiscount(bill);

        // Assert
        assertEquals(BigDecimal.valueOf(8.2), discountInfo.getDiscountAmount());
    }

    /**
     * Method under test: {@link DiscountServiceImpl#calculateDiscount(Bill)}
     */
    @Test
    void testCalculateDiscount4() {
        // Arrange
        ArrayList<AbstractDiscount> abstractDiscountList = new ArrayList<>();
        BigDecimal percent = BigDecimal.valueOf(100L);
        BigDecimal amount = BigDecimal.valueOf(42L);
        abstractDiscountList.add(new BillDiscount("level", 2, 2, true, percent, amount, BigDecimal.valueOf(42L)));
        when(this.discountRepository.findAll((org.springframework.data.domain.Sort) any()))
                .thenReturn(abstractDiscountList);
        Bill bill = mock(Bill.class);
        when(bill.originalAmount()).thenReturn(BigDecimal.valueOf(42L));

        // Act
        DiscountInfo actualCalculateDiscountResult = this.discountServiceImpl.calculateDiscount(bill);

        // Assert
        assertEquals("84", actualCalculateDiscountResult.getDiscountAmount().toString());
        verify(this.discountRepository).findAll((org.springframework.data.domain.Sort) any());
        verify(bill).originalAmount();
    }

    /**
     * Method under test: {@link DiscountServiceImpl#calculateDiscount(Bill)}
     */
    @Test
    void testCalculateDiscount5() {
        // Arrange
        ArrayList<AbstractDiscount> abstractDiscountList = new ArrayList<>();
        BigDecimal percent = BigDecimal.valueOf(100L);
        BigDecimal amount = BigDecimal.valueOf(42L);
        abstractDiscountList.add(new BillDiscount("level", 2, 2, false, percent, amount, BigDecimal.valueOf(42L)));
        abstractDiscountList.add(new BillDiscount("level", 2, 2, false, percent, amount, BigDecimal.valueOf(42L)));
        when(this.discountRepository.findAll((org.springframework.data.domain.Sort) any()))
                .thenReturn(abstractDiscountList);
        Bill bill = mock(Bill.class);
        when(bill.originalAmount()).thenReturn(BigDecimal.valueOf(42L));

        // Act
        final DiscountInfo discountInfo = this.discountServiceImpl.calculateDiscount(bill);

        // Assert
        assertEquals(BigDecimal.valueOf(84), discountInfo.getDiscountAmount());
    }

    /**
     * Method under test: {@link DiscountServiceImpl#calculateDiscount(Bill)}
     */
    @Test
    void testCalculateDiscount6() {
        // Arrange
        BillDiscount billDiscount = mock(BillDiscount.class);
        when(billDiscount.isCombinedOnLevel()).thenReturn(false);
        when(billDiscount.getLevel()).thenReturn(1);
        when(billDiscount.apply(any())).thenReturn(BigDecimal.valueOf(42L));

        ArrayList<AbstractDiscount> abstractDiscountList = new ArrayList<>();
        abstractDiscountList.add(billDiscount);
        when(this.discountRepository.findAll((org.springframework.data.domain.Sort) any()))
                .thenReturn(abstractDiscountList);
        Bill bill = mock(Bill.class);
        BigDecimal valueOfResult = BigDecimal.valueOf(42L);
        when(bill.originalAmount()).thenReturn(valueOfResult);

        // Act
        DiscountInfo actualCalculateDiscountResult = this.discountServiceImpl.calculateDiscount(bill);

        // Assert
        BigDecimal discountAmount = actualCalculateDiscountResult.getDiscountAmount();
        assertEquals(valueOfResult, discountAmount);
        assertEquals("42", discountAmount.toString());
        verify(this.discountRepository).findAll((org.springframework.data.domain.Sort) any());
        verify(billDiscount, atLeast(1)).isCombinedOnLevel();
        verify(billDiscount, atLeast(1)).getLevel();
        verify(billDiscount).apply(any());
    }

    /**
     * Method under test: {@link DiscountServiceImpl#calculateDiscount(Bill)}
     */
    @Test
    void testCalculateDiscount7() {
        // Arrange
        ArrayList<AbstractDiscount> abstractDiscountList = new ArrayList<>();
        BigDecimal percent = BigDecimal.valueOf(10L);
        BigDecimal amount = BigDecimal.valueOf(0L);
        abstractDiscountList.add(new BillDiscount("level", 2, 2, true, percent, amount, BigDecimal.valueOf(42L)));
        abstractDiscountList.add(new BillDiscount("level", 2, 2, false, percent, amount, BigDecimal.valueOf(42L)));
        abstractDiscountList.add(new BillDiscount("level", 2, 1, true, percent, amount, BigDecimal.valueOf(10L)));
        when(this.discountRepository.findAll((org.springframework.data.domain.Sort) any()))
                .thenReturn(abstractDiscountList);
        Bill bill = mock(Bill.class);
        when(bill.originalAmount()).thenReturn(BigDecimal.valueOf(42L));

        // Act
        final DiscountInfo discountInfo = this.discountServiceImpl.calculateDiscount(bill);

        // Assert
        assertEquals(BigDecimal.valueOf(12.4), discountInfo.getDiscountAmount());
    }

    /**
     * Method under test: {@link DiscountServiceImpl#calculateDiscount(Bill)}
     */
    @Test
    void testCalculateDiscount8() {
        // Arrange
        BillDiscount billDiscount = mock(BillDiscount.class);
        when(billDiscount.isCombinedOnLevel()).thenReturn(false);
        when(billDiscount.getLevel()).thenReturn(1);
        when(billDiscount.apply(any())).thenReturn(BigDecimal.valueOf(0L));

        ArrayList<AbstractDiscount> abstractDiscountList = new ArrayList<>();
        abstractDiscountList.add(billDiscount);
        when(this.discountRepository.findAll((org.springframework.data.domain.Sort) any()))
                .thenReturn(abstractDiscountList);
        Bill bill = mock(Bill.class);
        BigDecimal valueOfResult = BigDecimal.valueOf(42L);
        when(bill.originalAmount()).thenReturn(valueOfResult);

        // Act
        DiscountInfo actualCalculateDiscountResult = this.discountServiceImpl.calculateDiscount(bill);

        // Assert
        BigDecimal expectedDiscountAmount = valueOfResult.ZERO;
        BigDecimal discountAmount = actualCalculateDiscountResult.getDiscountAmount();
        assertSame(expectedDiscountAmount, discountAmount);
        assertEquals("0", discountAmount.toString());
        verify(this.discountRepository).findAll((org.springframework.data.domain.Sort) any());
        verify(billDiscount, atLeast(1)).isCombinedOnLevel();
        verify(billDiscount, atLeast(1)).getLevel();
        verify(billDiscount).apply(any());
    }

    /**
     * Method under test: {@link DiscountServiceImpl#calculateDiscount(Bill)}
     */
    @Test
    void testCalculateDiscount9() {
        // Arrange
        ArrayList<AbstractDiscount> abstractDiscountList = new ArrayList<>();
        BigDecimal percent = BigDecimal.valueOf(10L);
        BigDecimal amount = BigDecimal.valueOf(0L);
        abstractDiscountList.add(new BillDiscount("level", 2, 2, false, percent, amount, BigDecimal.valueOf(42L)));
        abstractDiscountList.add(new BillDiscount("level", 2, 2, false, percent, amount, BigDecimal.valueOf(42L)));
        abstractDiscountList.add(new BillDiscount("level", 2, 1, true, percent, amount, BigDecimal.valueOf(10L)));
        when(this.discountRepository.findAll((org.springframework.data.domain.Sort) any()))
                .thenReturn(abstractDiscountList);
        Bill bill = mock(Bill.class);
        when(bill.originalAmount()).thenReturn(BigDecimal.valueOf(42L));

        // Act
        final DiscountInfo discountInfo = this.discountServiceImpl.calculateDiscount(bill);

        // Assert
        assertEquals(BigDecimal.valueOf(8.2), discountInfo.getDiscountAmount());
    }
}

