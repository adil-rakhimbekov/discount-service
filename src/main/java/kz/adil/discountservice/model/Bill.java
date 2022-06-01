package kz.adil.discountservice.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Transient;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class Bill implements Discountable {
    private User customer;
    private List<Item> items;

    @Transient
    @Override
    public BigDecimal originalAmount() {
        return items.stream()
                .map(Item.sum)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
