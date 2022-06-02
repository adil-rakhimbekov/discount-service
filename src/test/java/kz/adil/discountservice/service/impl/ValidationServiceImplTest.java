package kz.adil.discountservice.service.impl;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import kz.adil.discountservice.exception.ValidationException;
import kz.adil.discountservice.model.bill.Item;
import kz.adil.discountservice.model.bill.Product;
import kz.adil.discountservice.model.bill.ProductType;
import kz.adil.discountservice.dto.BillRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {ValidationServiceImpl.class})
@ExtendWith(SpringExtension.class)
class ValidationServiceImplTest {
    @Autowired
    private ValidationServiceImpl validationServiceImpl;

    /**
     * Method under test: {@link ValidationServiceImpl#validateRequest(BillRequest)}
     */
    @Test
    void testValidateRequest() {
        // Arrange
        BillRequest billRequest = new BillRequest();
        billRequest.setItems(new ArrayList<>());

        // Act and Assert
        assertThrows(ValidationException.class, () -> this.validationServiceImpl.validateRequest(billRequest));
    }

    /**
     * Method under test: {@link ValidationServiceImpl#validateRequest(BillRequest)}
     */
    @Test
    void testValidateRequest2() {
        // Arrange
        BillRequest billRequest = mock(BillRequest.class);
        when(billRequest.getItems()).thenReturn(new ArrayList<>());
        doNothing().when(billRequest).setItems(any());
        billRequest.setItems(new ArrayList<>());

        // Act and Assert
        assertThrows(ValidationException.class, () -> this.validationServiceImpl.validateRequest(billRequest));
        verify(billRequest, atLeast(1)).getItems();
        verify(billRequest).setItems(any());
    }

    /**
     * Method under test: {@link ValidationServiceImpl#validateRequest(BillRequest)}
     */
    @Test
    void testValidateRequest3() {
        // Arrange
        ArrayList<Item> itemList = new ArrayList<>();
        itemList.add(new Item());
        BillRequest billRequest = mock(BillRequest.class);
        when(billRequest.getItems()).thenReturn(itemList);
        doNothing().when(billRequest).setItems(any());
        billRequest.setItems(new ArrayList<>());

        // Act and Assert
        assertThrows(ValidationException.class, () -> this.validationServiceImpl.validateRequest(billRequest));
        verify(billRequest, atLeast(1)).getItems();
        verify(billRequest).setItems(any());
    }

    /**
     * Method under test: {@link ValidationServiceImpl#validateRequest(BillRequest)}
     */
    @Test
    void testValidateRequest4() {
        // Arrange
        BillRequest billRequest = null;

        // Act and Assert
        assertThrows(ValidationException.class, () -> this.validationServiceImpl.validateRequest(billRequest));
    }

    /**
     * Method under test: {@link ValidationServiceImpl#validateRequest(BillRequest)}
     */
    @Test
    void testValidateRequest5() {
        // Arrange
        Product product = new Product();
        product.setName("Name");
        product.setPrice(BigDecimal.valueOf(42L));
        product.setType(new ProductType("Name"));
        Item item = mock(Item.class);
        when(item.getQuantity()).thenReturn(1);
        when(item.getProduct()).thenReturn(product);

        ArrayList<Item> itemList = new ArrayList<>();
        itemList.add(item);
        BillRequest billRequest = mock(BillRequest.class);
        when(billRequest.getItems()).thenReturn(itemList);
        doNothing().when(billRequest).setItems(any());
        billRequest.setItems(new ArrayList<>());

        // Act
        this.validationServiceImpl.validateRequest(billRequest);

        // Assert
        verify(billRequest, atLeast(1)).getItems();
        verify(billRequest).setItems(any());
        verify(item).getQuantity();
        verify(item).getProduct();
    }

    /**
     * Method under test: {@link ValidationServiceImpl#validateRequest(BillRequest)}
     */
    @Test
    void testValidateRequest6() {
        // Arrange
        Product product = new Product();
        product.setName("Name");
        product.setPrice(BigDecimal.valueOf(42L));
        product.setType(new ProductType("Name"));
        Item item = mock(Item.class);
        when(item.getQuantity()).thenThrow(new ValidationException(1, "An error occurred"));
        when(item.getProduct()).thenReturn(product);

        ArrayList<Item> itemList = new ArrayList<>();
        itemList.add(item);
        BillRequest billRequest = mock(BillRequest.class);
        when(billRequest.getItems()).thenReturn(itemList);
        doNothing().when(billRequest).setItems(any());
        billRequest.setItems(new ArrayList<>());

        // Act and Assert
        assertThrows(ValidationException.class, () -> this.validationServiceImpl.validateRequest(billRequest));
        verify(billRequest, atLeast(1)).getItems();
        verify(billRequest).setItems(any());
        verify(item).getQuantity();
        verify(item).getProduct();
    }

    /**
     * Method under test: {@link ValidationServiceImpl#validateRequest(BillRequest)}
     */
    @Test
    void testValidateRequest7() {
        // Arrange
        Product product = new Product();
        product.setName("Name");
        product.setPrice(BigDecimal.valueOf(42L));
        product.setType(new ProductType("Name"));
        Item item = mock(Item.class);
        when(item.getQuantity()).thenReturn(0);
        when(item.getProduct()).thenReturn(product);

        ArrayList<Item> itemList = new ArrayList<>();
        itemList.add(item);
        BillRequest billRequest = mock(BillRequest.class);
        when(billRequest.getItems()).thenReturn(itemList);
        doNothing().when(billRequest).setItems(any());
        billRequest.setItems(new ArrayList<>());

        // Act and Assert
        assertThrows(ValidationException.class, () -> this.validationServiceImpl.validateRequest(billRequest));
        verify(billRequest, atLeast(1)).getItems();
        verify(billRequest).setItems(any());
        verify(item).getQuantity();
        verify(item).getProduct();
    }

    /**
     * Method under test: {@link ValidationServiceImpl#validateRequest(BillRequest)}
     */
    @Test
    void testValidateRequest8() {
        // Arrange
        Product product = mock(Product.class);
        when(product.getType()).thenThrow(new ValidationException(1, "An error occurred"));
        when(product.getPrice()).thenReturn(BigDecimal.valueOf(42L));
        when(product.getName()).thenReturn("Name");
        doNothing().when(product).setName(any());
        doNothing().when(product).setPrice(any());
        doNothing().when(product).setType(any());
        product.setName("Name");
        product.setPrice(BigDecimal.valueOf(42L));
        product.setType(new ProductType("Name"));
        Item item = mock(Item.class);
        when(item.getQuantity()).thenReturn(1);
        when(item.getProduct()).thenReturn(product);

        ArrayList<Item> itemList = new ArrayList<>();
        itemList.add(item);
        BillRequest billRequest = mock(BillRequest.class);
        when(billRequest.getItems()).thenReturn(itemList);
        doNothing().when(billRequest).setItems(any());
        billRequest.setItems(new ArrayList<>());

        // Act and Assert
        assertThrows(ValidationException.class, () -> this.validationServiceImpl.validateRequest(billRequest));
        verify(billRequest, atLeast(1)).getItems();
        verify(billRequest).setItems(any());
        verify(item).getProduct();
        verify(product, atLeast(1)).getName();
        verify(product).getPrice();
        verify(product).getType();
        verify(product).setName(any());
        verify(product).setPrice(any());
        verify(product).setType(any());
    }

    /**
     * Method under test: {@link ValidationServiceImpl#validateRequest(BillRequest)}
     */
    @Test
    void testValidateRequest9() {
        // Arrange
        Product product = mock(Product.class);
        when(product.getType()).thenReturn(new ProductType(""));
        when(product.getPrice()).thenReturn(BigDecimal.valueOf(42L));
        when(product.getName()).thenReturn("Name");
        doNothing().when(product).setName(any());
        doNothing().when(product).setPrice(any());
        doNothing().when(product).setType(any());
        product.setName("Name");
        product.setPrice(BigDecimal.valueOf(42L));
        product.setType(new ProductType("Name"));
        Item item = mock(Item.class);
        when(item.getQuantity()).thenReturn(1);
        when(item.getProduct()).thenReturn(product);

        ArrayList<Item> itemList = new ArrayList<>();
        itemList.add(item);
        BillRequest billRequest = mock(BillRequest.class);
        when(billRequest.getItems()).thenReturn(itemList);
        doNothing().when(billRequest).setItems(any());
        billRequest.setItems(new ArrayList<>());

        // Act and Assert
        assertThrows(ValidationException.class, () -> this.validationServiceImpl.validateRequest(billRequest));
        verify(billRequest, atLeast(1)).getItems();
        verify(billRequest).setItems(any());
        verify(item).getProduct();
        verify(product, atLeast(1)).getName();
        verify(product).getPrice();
        verify(product, atLeast(1)).getType();
        verify(product).setName(any());
        verify(product).setPrice(any());
        verify(product).setType(any());
    }

    /**
     * Method under test: {@link ValidationServiceImpl#validateRequest(BillRequest)}
     */
    @Test
    void testValidateRequest10() {
        // Arrange
        Product product = mock(Product.class);
        when(product.getType()).thenReturn(null);
        when(product.getPrice()).thenReturn(BigDecimal.valueOf(42L));
        when(product.getName()).thenReturn("Name");
        doNothing().when(product).setName(any());
        doNothing().when(product).setPrice(any());
        doNothing().when(product).setType(any());
        product.setName("Name");
        product.setPrice(BigDecimal.valueOf(42L));
        product.setType(new ProductType("Name"));
        Item item = mock(Item.class);
        when(item.getQuantity()).thenReturn(1);
        when(item.getProduct()).thenReturn(product);

        ArrayList<Item> itemList = new ArrayList<>();
        itemList.add(item);
        BillRequest billRequest = mock(BillRequest.class);
        when(billRequest.getItems()).thenReturn(itemList);
        doNothing().when(billRequest).setItems(any());
        billRequest.setItems(new ArrayList<>());

        // Act and Assert
        assertThrows(ValidationException.class, () -> this.validationServiceImpl.validateRequest(billRequest));
        verify(billRequest, atLeast(1)).getItems();
        verify(billRequest).setItems(any());
        verify(item).getProduct();
        verify(product, atLeast(1)).getName();
        verify(product).getPrice();
        verify(product).getType();
        verify(product).setName(any());
        verify(product).setPrice(any());
        verify(product).setType(any());
    }

    /**
     * Method under test: {@link ValidationServiceImpl#validateRequest(BillRequest)}
     */
    @Test
    void testValidateRequest11() {
        // Arrange
        ProductType productType = mock(ProductType.class);
        when(productType.getName()).thenReturn("Name");
        Product product = mock(Product.class);
        when(product.getType()).thenReturn(productType);
        when(product.getPrice()).thenReturn(BigDecimal.valueOf(-1L));
        when(product.getName()).thenReturn("Name");
        doNothing().when(product).setName(any());
        doNothing().when(product).setPrice(any());
        doNothing().when(product).setType(any());
        product.setName("Name");
        product.setPrice(BigDecimal.valueOf(42L));
        product.setType(new ProductType("Name"));
        Item item = mock(Item.class);
        when(item.getQuantity()).thenReturn(-1);
        when(item.getProduct()).thenReturn(product);

        ArrayList<Item> itemList = new ArrayList<>();
        itemList.add(item);
        BillRequest billRequest = mock(BillRequest.class);
        when(billRequest.getItems()).thenReturn(itemList);
        doNothing().when(billRequest).setItems(any());
        billRequest.setItems(new ArrayList<>());

        // Act and Assert
        assertThrows(ValidationException.class, () -> this.validationServiceImpl.validateRequest(billRequest));
        verify(billRequest, atLeast(1)).getItems();
        verify(billRequest).setItems(any());
        verify(item).getProduct();
        verify(product, atLeast(1)).getName();
        verify(product, atLeast(1)).getPrice();
        verify(product, atLeast(1)).getType();
        verify(product).setName(any());
        verify(product).setPrice(any());
        verify(product).setType(any());
        verify(productType).getName();
    }

    /**
     * Method under test: {@link ValidationServiceImpl#validateRequest(BillRequest)}
     */
    @Test
    void testValidateRequest12() {
        // Arrange
        BillRequest billRequest = mock(BillRequest.class);
        when(billRequest.getItems()).thenReturn(null);

        // Act and Assert
        assertThrows(ValidationException.class, () -> this.validationServiceImpl.validateRequest(billRequest));
        verify(billRequest, atLeast(1)).getItems();
    }

    /**
     * Method under test: {@link ValidationServiceImpl#validateRequest(BillRequest)}
     */
    @Test
    void testValidateRequest13() {
        // Arrange
        ProductType productType = mock(ProductType.class);
        when(productType.getName()).thenReturn("Name");
        Product product = mock(Product.class);
        when(product.getType()).thenReturn(productType);
        when(product.getPrice()).thenReturn(BigDecimal.valueOf(-1L));
        when(product.getName()).thenReturn(null);
        doNothing().when(product).setName(any());
        doNothing().when(product).setPrice(any());
        doNothing().when(product).setType(any());
        product.setName("Name");
        product.setPrice(BigDecimal.valueOf(42L));
        product.setType(new ProductType("Name"));
        Item item = mock(Item.class);
        when(item.getQuantity()).thenReturn(-1);
        when(item.getProduct()).thenReturn(product);

        ArrayList<Item> itemList = new ArrayList<>();
        itemList.add(item);
        BillRequest billRequest = mock(BillRequest.class);
        when(billRequest.getItems()).thenReturn(itemList);
        doNothing().when(billRequest).setItems(any());
        billRequest.setItems(new ArrayList<>());

        // Act and Assert
        assertThrows(ValidationException.class, () -> this.validationServiceImpl.validateRequest(billRequest));
        verify(billRequest, atLeast(1)).getItems();
        verify(billRequest).setItems(any());
        verify(item).getProduct();
        verify(product, atLeast(1)).getName();
    }

    /**
     * Method under test: {@link ValidationServiceImpl#validateRequest(BillRequest)}
     */
    @Test
    void testValidateRequest14() {
        // Arrange
        ProductType productType = mock(ProductType.class);
        when(productType.getName()).thenReturn("Name");
        Product product = mock(Product.class);
        when(product.getType()).thenReturn(productType);
        when(product.getPrice()).thenReturn(BigDecimal.valueOf(-1L));
        when(product.getName()).thenReturn("");
        doNothing().when(product).setName(any());
        doNothing().when(product).setPrice(any());
        doNothing().when(product).setType(any());
        product.setName("Name");
        product.setPrice(BigDecimal.valueOf(42L));
        product.setType(new ProductType("Name"));
        Item item = mock(Item.class);
        when(item.getQuantity()).thenReturn(-1);
        when(item.getProduct()).thenReturn(product);

        ArrayList<Item> itemList = new ArrayList<>();
        itemList.add(item);
        BillRequest billRequest = mock(BillRequest.class);
        when(billRequest.getItems()).thenReturn(itemList);
        doNothing().when(billRequest).setItems(any());
        billRequest.setItems(new ArrayList<>());

        // Act and Assert
        assertThrows(ValidationException.class, () -> this.validationServiceImpl.validateRequest(billRequest));
        verify(billRequest, atLeast(1)).getItems();
        verify(billRequest).setItems(any());
        verify(item).getProduct();
        verify(product, atLeast(1)).getName();
    }

    /**
     * Method under test: {@link ValidationServiceImpl#validateRequest(BillRequest)}
     */
    @Test
    void testValidateRequest15() {
        // Arrange
        ProductType productType = mock(ProductType.class);
        when(productType.getName()).thenReturn("Name");
        Product product = mock(Product.class);
        when(product.getType()).thenReturn(productType);
        when(product.getPrice()).thenReturn(null);
        when(product.getName()).thenReturn("Name");
        doNothing().when(product).setName(any());
        doNothing().when(product).setPrice(any());
        doNothing().when(product).setType(any());
        product.setName("Name");
        product.setPrice(BigDecimal.valueOf(42L));
        product.setType(new ProductType("Name"));
        Item item = mock(Item.class);
        when(item.getQuantity()).thenReturn(-1);
        when(item.getProduct()).thenReturn(product);

        ArrayList<Item> itemList = new ArrayList<>();
        itemList.add(item);
        BillRequest billRequest = mock(BillRequest.class);
        when(billRequest.getItems()).thenReturn(itemList);
        doNothing().when(billRequest).setItems(any());
        billRequest.setItems(new ArrayList<>());

        // Act and Assert
        assertThrows(ValidationException.class, () -> this.validationServiceImpl.validateRequest(billRequest));
        verify(billRequest, atLeast(1)).getItems();
        verify(billRequest).setItems(any());
        verify(item).getProduct();
        verify(product, atLeast(1)).getName();
    }

    /**
     * Method under test: {@link ValidationServiceImpl#validateRequest(BillRequest)}
     */
    @Test
    void testValidateRequest16() {
        // Arrange
        ProductType productType = mock(ProductType.class);
        when(productType.getName()).thenReturn("Name");
        Product product = mock(Product.class);
        when(product.getType()).thenReturn(productType);
        when(product.getPrice()).thenReturn(BigDecimal.ZERO);
        when(product.getName()).thenReturn("Name");
        doNothing().when(product).setName(any());
        doNothing().when(product).setPrice(any());
        doNothing().when(product).setType(any());
        product.setName("Name");
        product.setPrice(BigDecimal.valueOf(42L));
        product.setType(new ProductType("Name"));
        Item item = mock(Item.class);
        when(item.getQuantity()).thenReturn(-1);
        when(item.getProduct()).thenReturn(product);

        ArrayList<Item> itemList = new ArrayList<>();
        itemList.add(item);
        BillRequest billRequest = mock(BillRequest.class);
        when(billRequest.getItems()).thenReturn(itemList);
        doNothing().when(billRequest).setItems(any());
        billRequest.setItems(new ArrayList<>());

        // Act and Assert
        assertThrows(ValidationException.class, () -> this.validationServiceImpl.validateRequest(billRequest));
        verify(billRequest, atLeast(1)).getItems();
        verify(billRequest).setItems(any());
        verify(item).getProduct();
        verify(product, atLeast(1)).getName();
    }
}

