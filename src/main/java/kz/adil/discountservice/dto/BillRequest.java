package kz.adil.discountservice.dto;

import kz.adil.discountservice.model.bill.Item;
import lombok.Data;

import java.util.List;

@Data
public class BillRequest {
    private List<Item> items;
}
