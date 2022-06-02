package kz.adil.discountservice.model.bill;

import lombok.Data;
import org.springframework.data.annotation.Transient;

import java.math.BigDecimal;
import java.util.function.Function;

@Data
public class Item implements Discountable {
    private Product product;
    private int quantity;

    static Function<Item, BigDecimal> sum = item -> item.getProduct().getPrice().multiply(BigDecimal.valueOf(item.getQuantity()));

    @Transient
    @Override
    public BigDecimal originalAmount() {
        return Item.sum.apply(this);
    }
}
