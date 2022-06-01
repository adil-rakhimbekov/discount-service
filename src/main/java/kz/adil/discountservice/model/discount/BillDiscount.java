package kz.adil.discountservice.model.discount;

import kz.adil.discountservice.model.Bill;
import lombok.*;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class BillDiscount extends AbstractDiscount {
    private BigDecimal roundingAmount;

    public BillDiscount(String name, int level, int priority, boolean isCombinedOnLevel,  BigDecimal percent, BigDecimal amount, BigDecimal roundingAmount) {
        super(name, level, priority, isCombinedOnLevel, percent, amount);
        this.roundingAmount = roundingAmount;
    }

    @Override
    public BigDecimal apply(Bill bill) {
        final BigDecimal originalAmount = bill.originalAmount();
        final BigDecimal remainder = originalAmount.remainder(roundingAmount);
        final BigDecimal discountableAmount = originalAmount.subtract(remainder);
        return discountableAmount.multiply(super.getPercent())
                .divide(BigDecimal.valueOf(100), RoundingMode.UNNECESSARY)
                .add(super.getAmount());
    }
}
