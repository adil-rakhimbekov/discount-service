package kz.adil.discountservice.model.discount;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

import kz.adil.discountservice.model.bill.Bill;
import kz.adil.discountservice.model.bill.Item;
import kz.adil.discountservice.model.bill.Product;
import kz.adil.discountservice.model.bill.ProductType;
import kz.adil.discountservice.model.user.User;
import kz.adil.discountservice.model.user.UserType;
import org.junit.jupiter.api.Test;

class LoyaltyDiscountTest {

    /**
     * Method under test: {@link LoyaltyDiscount#apply(Bill)}
     */
    @Test
    void testApply() {
        // Arrange
        BigDecimal percent = BigDecimal.valueOf(42L);
        BigDecimal amount = BigDecimal.valueOf(42L);
        ArrayList<ProductType> includedTypes = new ArrayList<>();
        LoyaltyDiscount loyaltyDiscount = new LoyaltyDiscount("Name", 1, 1, true, percent, amount, UserType.EMPLOYEE,
                includedTypes, new ArrayList<>(), null);
        User user = mock(User.class);
        when(user.getTypes()).thenReturn(new ArrayList<>());
        Bill bill = mock(Bill.class);
        when(bill.getCustomer()).thenReturn(user);

        // Act
        BigDecimal actualApplyResult = loyaltyDiscount.apply(bill);

        // Assert
        assertEquals(actualApplyResult.ZERO, actualApplyResult);
        assertEquals("0", actualApplyResult.toString());
        verify(bill).getCustomer();
        verify(user).getTypes();
    }

    /**
     * Method under test: {@link LoyaltyDiscount#apply(Bill)}
     */
    @Test
    void testApply2() {
        // Arrange
        BigDecimal percent = BigDecimal.valueOf(42L);
        BigDecimal amount = BigDecimal.valueOf(42L);
        ArrayList<ProductType> includedTypes = new ArrayList<>();
        LoyaltyDiscount loyaltyDiscount = new LoyaltyDiscount("Name", 1, 1, true, percent, amount, UserType.EMPLOYEE,
                includedTypes, new ArrayList<>(), Period.ofYears(2));
        Bill bill = mock(Bill.class);
        User user = mock(User.class);
        final List<UserType> userTypes = new ArrayList<>();
        userTypes.add(UserType.EMPLOYEE);
        when(user.getTypes()).thenReturn(userTypes);
        when(user.getCreatedDate()).thenReturn(LocalDate.now().minusYears(4));
        when(bill.getCustomer()).thenReturn(user);
        final List<Item> items = new ArrayList<>();
        final Item item = new Item();
        final Product product = new Product();
        product.setPrice(BigDecimal.ONE);
        item.setProduct(product);
        item.setQuantity(10);
        items.add(item);
        when(bill.getItems()).thenReturn(items);

        // Act
        BigDecimal actualApplyResult = loyaltyDiscount.apply(bill);

        // Assert
        assertEquals(BigDecimal.valueOf(46.2), actualApplyResult);
        assertEquals("46.2", actualApplyResult.toString());
        verify(bill).getCustomer();
        verify(user).getTypes();
        verify(user).getCreatedDate();
    }

    /**
     * Method under test: {@link LoyaltyDiscount#apply(Bill)}
     */
    @Test
    void testApply3() {
        // Arrange
        BigDecimal percent = BigDecimal.valueOf(42L);
        BigDecimal amount = BigDecimal.valueOf(42L);
        ArrayList<ProductType> includedTypes = new ArrayList<>();
        LoyaltyDiscount loyaltyDiscount = new LoyaltyDiscount("Name", 1, 1, true, percent, amount, UserType.EMPLOYEE,
                includedTypes, new ArrayList<>(), Period.ofYears(2));
        Bill bill = mock(Bill.class);
        User user = mock(User.class);
        final List<UserType> userTypes = new ArrayList<>();
        userTypes.add(UserType.EMPLOYEE);
        when(user.getTypes()).thenReturn(userTypes);
        when(user.getCreatedDate()).thenReturn(LocalDate.now().minusYears(1));
        when(bill.getCustomer()).thenReturn(user);
        final List<Item> items = new ArrayList<>();
        final Item item = new Item();
        final Product product = new Product();
        product.setPrice(BigDecimal.ONE);
        item.setProduct(product);
        item.setQuantity(10);
        items.add(item);
        when(bill.getItems()).thenReturn(items);

        // Act
        BigDecimal actualApplyResult = loyaltyDiscount.apply(bill);

        // Assert
        assertEquals(BigDecimal.ZERO, actualApplyResult);
        assertEquals("0", actualApplyResult.toString());
        verify(bill).getCustomer();
        verify(user).getTypes();
        verify(user).getCreatedDate();
    }

    /**
     * Method under test: {@link LoyaltyDiscount#apply(Bill)}
     */
    @Test
    void testApply4() {
        // Arrange
        BigDecimal percent = BigDecimal.valueOf(42L);
        BigDecimal amount = BigDecimal.valueOf(42L);
        ArrayList<ProductType> includedTypes = new ArrayList<>();
        final ProductType clothes = new ProductType("CLOTHES");
        includedTypes.add(clothes);
        LoyaltyDiscount loyaltyDiscount = new LoyaltyDiscount("Name", 1, 1, true, percent, amount, UserType.EMPLOYEE,
                includedTypes, new ArrayList<>(), Period.ofYears(2));
        Bill bill = mock(Bill.class);
        User user = mock(User.class);
        final List<UserType> userTypes = new ArrayList<>();
        userTypes.add(UserType.EMPLOYEE);
        when(user.getTypes()).thenReturn(userTypes);
        when(user.getCreatedDate()).thenReturn(LocalDate.now().minusYears(4));
        when(bill.getCustomer()).thenReturn(user);
        final List<Item> items = new ArrayList<>();
        final Item item = new Item();
        final Product product = new Product();
        product.setPrice(BigDecimal.ONE);
        product.setType(clothes);
        item.setProduct(product);
        item.setQuantity(10);
        items.add(item);
        when(bill.getItems()).thenReturn(items);

        // Act
        BigDecimal actualApplyResult = loyaltyDiscount.apply(bill);

        // Assert
        assertEquals(BigDecimal.valueOf(46.2), actualApplyResult);
        assertEquals("46.2", actualApplyResult.toString());
        verify(bill).getCustomer();
        verify(user).getTypes();
        verify(user).getCreatedDate();
    }

    /**
     * Method under test: {@link LoyaltyDiscount#apply(Bill)}
     */
    @Test
    void testApply5() {
        // Arrange
        BigDecimal percent = BigDecimal.valueOf(42L);
        BigDecimal amount = BigDecimal.valueOf(42L);
        ArrayList<ProductType> excludedTypes = new ArrayList<>();
        final ProductType grocery = new ProductType(ProductType.GROCERY);
        excludedTypes.add(grocery);
        LoyaltyDiscount loyaltyDiscount = new LoyaltyDiscount("Name", 1, 1, true, percent, amount, UserType.EMPLOYEE,
                new ArrayList<>(), excludedTypes, Period.ofYears(2));
        Bill bill = mock(Bill.class);
        User user = mock(User.class);
        final List<UserType> userTypes = new ArrayList<>();
        userTypes.add(UserType.EMPLOYEE);
        when(user.getTypes()).thenReturn(userTypes);
        when(user.getCreatedDate()).thenReturn(LocalDate.now().minusYears(4));
        when(bill.getCustomer()).thenReturn(user);
        final List<Item> items = new ArrayList<>();
        final Item item = new Item();
        final Product product = new Product();
        product.setPrice(BigDecimal.ONE);
        product.setType(grocery);
        item.setProduct(product);
        item.setQuantity(10);
        items.add(item);
        when(bill.getItems()).thenReturn(items);

        // Act
        BigDecimal actualApplyResult = loyaltyDiscount.apply(bill);

        // Assert
        assertEquals(BigDecimal.ZERO, actualApplyResult);
        assertEquals("0", actualApplyResult.toString());
        verify(bill).getCustomer();
        verify(user).getTypes();
        verify(user).getCreatedDate();
    }

}

