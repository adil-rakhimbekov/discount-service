package kz.adil.discountservice.model.dto;

import kz.adil.discountservice.model.Item;
import lombok.Data;

import java.util.List;

@Data
public class BillRequest {
    private List<Item> items;
}
