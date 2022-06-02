package kz.adil.discountservice.dto;

import kz.adil.discountservice.model.bill.Item;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
public class BillRequest {
    @NotEmpty
    private List<Item> items;
}
