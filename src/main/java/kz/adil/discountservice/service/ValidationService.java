package kz.adil.discountservice.service;

import kz.adil.discountservice.model.dto.BillRequest;

public interface ValidationService {
    int REQUEST_IS_EMPTY = 1001;
    int ITEM_LIST_IS_EMPTY = 1002;
    int INVALID_ITEM = 1003;
    int QUANTITY_ZERO_OR_NEGATIVE = 1004;

    void validateRequest(BillRequest billRequest);
}
