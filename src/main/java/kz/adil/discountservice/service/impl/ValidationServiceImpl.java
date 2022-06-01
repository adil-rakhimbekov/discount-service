package kz.adil.discountservice.service.impl;

import kz.adil.discountservice.exception.ValidationException;
import kz.adil.discountservice.model.Item;
import kz.adil.discountservice.model.Product;
import kz.adil.discountservice.model.dto.BillRequest;
import kz.adil.discountservice.service.ValidationService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class ValidationServiceImpl implements ValidationService {
    @Override
    public void validateRequest(BillRequest billRequest) {
        if (billRequest == null) {
            throw new ValidationException(ValidationService.REQUEST_IS_EMPTY, "Request is empty");
        }
        if (billRequest.getItems() == null || billRequest.getItems().isEmpty()) {
            throw new ValidationException(ValidationService.ITEM_LIST_IS_EMPTY, "Item list is empty");
        }
        for (Item item : billRequest.getItems()) {
            final Product product = item.getProduct();
            if (product == null || product.getName() == null || product.getName().isBlank() || product.getPrice() == null
                    || product.getType() == null || product.getType().getName().isBlank()
                    || product.getPrice().equals(BigDecimal.ZERO) || product.getPrice().compareTo(BigDecimal.ZERO) < 0) {
                throw new ValidationException(ValidationService.INVALID_ITEM, "Invalid product");
            }
            if (item.getQuantity() <= 0) {
                throw new ValidationException(ValidationService.QUANTITY_ZERO_OR_NEGATIVE, "The quantity must be greater than zero");
            }
        }
    }
}
