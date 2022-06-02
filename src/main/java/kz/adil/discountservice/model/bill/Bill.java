package kz.adil.discountservice.model.bill;

import kz.adil.discountservice.model.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Transient;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
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
