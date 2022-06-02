package kz.adil.discountservice.model.discount;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;

import kz.adil.discountservice.model.bill.Bill;
import kz.adil.discountservice.model.bill.Item;
import kz.adil.discountservice.model.bill.Product;
import kz.adil.discountservice.model.bill.ProductType;
import kz.adil.discountservice.model.user.User;
import kz.adil.discountservice.model.user.UserType;
import org.junit.jupiter.api.Test;

class ItemDiscountTest {
    /**
     * Method under test: {@link ItemDiscount#apply(Bill)}
     */
    @Test
    void testApply() {
        // Arrange
        BigDecimal percent = BigDecimal.valueOf(42L);
        BigDecimal amount = BigDecimal.valueOf(42L);
        ArrayList<ProductType> includedTypes = new ArrayList<>();
        ItemDiscount itemDiscount = new ItemDiscount("Name", 1, 1, true, percent, amount, UserType.EMPLOYEE, includedTypes,
                new ArrayList<>());
        User user = mock(User.class);
        when(user.getTypes()).thenReturn(new ArrayList<>());
        Bill bill = mock(Bill.class);
        when(bill.getCustomer()).thenReturn(user);

        // Act
        BigDecimal actualApplyResult = itemDiscount.apply(bill);

        // Assert
        assertEquals(actualApplyResult.ZERO, actualApplyResult);
        assertEquals("0", actualApplyResult.toString());
        verify(bill).getCustomer();
        verify(user).getTypes();
    }

    /**
     * Method under test: {@link ItemDiscount#apply(Bill)}
     */
    @Test
    void testApply2() {
        // Arrange
        BigDecimal percent = BigDecimal.valueOf(42L);
        BigDecimal amount = BigDecimal.valueOf(42L);
        ArrayList<ProductType> includedTypes = new ArrayList<>();
        ItemDiscount itemDiscount = new ItemDiscount("Name", 1, 1, true, percent, amount, UserType.EMPLOYEE, includedTypes,
                new ArrayList<>());

        ArrayList<UserType> userTypeList = new ArrayList<>();
        userTypeList.add(UserType.EMPLOYEE);
        User user = mock(User.class);
        when(user.getTypes()).thenReturn(userTypeList);
        Bill bill = mock(Bill.class);
        when(bill.getItems()).thenReturn(new ArrayList<>());
        when(bill.getCustomer()).thenReturn(user);

        // Act
        BigDecimal actualApplyResult = itemDiscount.apply(bill);

        // Assert
        assertEquals(actualApplyResult.ZERO, actualApplyResult);
        assertEquals("0", actualApplyResult.toString());
        verify(bill).getItems();
        verify(bill).getCustomer();
        verify(user).getTypes();
    }

    /**
     * Method under test: {@link ItemDiscount#apply(Bill)}
     */
    @Test
    void testApply3() {
        // Arrange
        ArrayList<ProductType> includedList = new ArrayList<>();
        includedList.add(new ProductType("Name"));
        ArrayList<ProductType> excludedList = new ArrayList<>();
        excludedList.add(new ProductType("Name1"));
        BigDecimal percent = BigDecimal.valueOf(42L);
        BigDecimal amount = BigDecimal.valueOf(42L);
        ItemDiscount itemDiscount = new ItemDiscount("Name", 1, 1, true, percent, amount, UserType.EMPLOYEE,
                includedList, excludedList);

        ArrayList<UserType> userTypeList = new ArrayList<>();
        userTypeList.add(UserType.EMPLOYEE);
        User user = mock(User.class);
        when(user.getTypes()).thenReturn(userTypeList);

        Product product = new Product();
        product.setName("Name");
        product.setPrice(BigDecimal.valueOf(42L));
        product.setType(null);

        Item item = new Item();
        item.setProduct(product);

        ArrayList<Item> itemList = new ArrayList<>();
        itemList.add(item);
        Bill bill = mock(Bill.class);
        when(bill.getItems()).thenReturn(itemList);
        when(bill.getCustomer()).thenReturn(user);

        // Act
        BigDecimal actualApplyResult = itemDiscount.apply(bill);

        // Assert
        assertEquals(BigDecimal.ZERO, actualApplyResult);
        assertEquals("0", actualApplyResult.toString());
        verify(bill).getItems();
        verify(bill).getCustomer();
        verify(user).getTypes();
    }

    /**
     * Method under test: {@link ItemDiscount#apply(Bill)}
     */
    @Test
    void testApply4() {
        // Arrange
        BigDecimal percent = BigDecimal.valueOf(42L);
        BigDecimal amount = BigDecimal.valueOf(42L);
        ArrayList<ProductType> includedTypes = new ArrayList<>();
        ItemDiscount itemDiscount = new ItemDiscount("Name", 1, 1, true, percent, amount, UserType.EMPLOYEE, includedTypes,
                new ArrayList<>());

        ArrayList<UserType> userTypeList = new ArrayList<>();
        userTypeList.add(UserType.EMPLOYEE);
        User user = mock(User.class);
        when(user.getTypes()).thenReturn(userTypeList);

        Product product = new Product();
        product.setName("Name");
        product.setPrice(BigDecimal.valueOf(42L));
        product.setType(new ProductType("Name"));

        Item item = new Item();
        item.setProduct(product);

        ArrayList<Item> itemList = new ArrayList<>();
        itemList.add(item);
        Bill bill = mock(Bill.class);
        when(bill.getItems()).thenReturn(itemList);
        when(bill.getCustomer()).thenReturn(user);

        // Act
        BigDecimal actualApplyResult = itemDiscount.apply(bill);

        // Assert
        assertEquals("42", actualApplyResult.toString());
        verify(bill).getItems();
        verify(bill).getCustomer();
        verify(user).getTypes();
    }

    /**
     * Method under test: {@link ItemDiscount#apply(Bill)}
     */
    @Test
    void testApply5() {
        // Arrange
        ArrayList<ProductType> productTypeList = new ArrayList<>();
        productTypeList.add(new ProductType("Name"));
        BigDecimal percent = BigDecimal.valueOf(42L);
        BigDecimal amount = BigDecimal.valueOf(42L);
        ItemDiscount itemDiscount = new ItemDiscount("Name", 1, 1, true, percent, amount, UserType.EMPLOYEE,
                productTypeList, new ArrayList<>());

        ArrayList<UserType> userTypeList = new ArrayList<>();
        userTypeList.add(UserType.EMPLOYEE);
        User user = mock(User.class);
        when(user.getTypes()).thenReturn(userTypeList);

        Product product = new Product();
        product.setName("Name");
        product.setPrice(BigDecimal.valueOf(42L));
        product.setType(new ProductType("Name"));

        Item item = new Item();
        item.setProduct(product);

        ArrayList<Item> itemList = new ArrayList<>();
        itemList.add(item);
        Bill bill = mock(Bill.class);
        when(bill.getItems()).thenReturn(itemList);
        when(bill.getCustomer()).thenReturn(user);

        // Act
        BigDecimal actualApplyResult = itemDiscount.apply(bill);

        // Assert
        assertEquals("42", actualApplyResult.toString());
        verify(bill).getItems();
        verify(bill).getCustomer();
        verify(user).getTypes();
    }

    /**
     * Method under test: {@link ItemDiscount#apply(Bill)}
     */
    @Test
    void testApply6() {
        // Arrange
        ArrayList<ProductType> productTypeList = new ArrayList<>();
        productTypeList.add(new ProductType("Name"));
        BigDecimal percent = BigDecimal.valueOf(42L);
        BigDecimal amount = BigDecimal.valueOf(42L);
        ItemDiscount itemDiscount = new ItemDiscount("Name", 1, 1, true, percent, amount, UserType.EMPLOYEE,
                new ArrayList<>(), productTypeList);

        ArrayList<UserType> userTypeList = new ArrayList<>();
        userTypeList.add(UserType.EMPLOYEE);
        User user = mock(User.class);
        when(user.getTypes()).thenReturn(userTypeList);

        Product product = new Product();
        product.setName("Name");
        product.setPrice(BigDecimal.valueOf(42L));
        product.setType(new ProductType("Name"));

        Item item = new Item();
        item.setProduct(product);

        ArrayList<Item> itemList = new ArrayList<>();
        itemList.add(item);
        Bill bill = mock(Bill.class);
        when(bill.getItems()).thenReturn(itemList);
        when(bill.getCustomer()).thenReturn(user);

        // Act
        BigDecimal actualApplyResult = itemDiscount.apply(bill);

        // Assert
        assertEquals(actualApplyResult.ZERO, actualApplyResult);
        assertEquals("0", actualApplyResult.toString());
        verify(bill).getItems();
        verify(bill).getCustomer();
        verify(user).getTypes();
    }
}

