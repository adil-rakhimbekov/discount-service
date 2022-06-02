package kz.adil.discountservice.model.discount;

import kz.adil.discountservice.model.bill.Bill;
import kz.adil.discountservice.model.bill.Item;
import kz.adil.discountservice.model.bill.ProductType;
import kz.adil.discountservice.model.user.User;
import kz.adil.discountservice.model.user.UserType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class LoyaltyDiscount extends ItemDiscount {
    private Period loyaltyYears;

    public LoyaltyDiscount(String name, int level, int priority, boolean isCombinedOnLevel, BigDecimal percent, BigDecimal amount,
                           UserType userType, List<ProductType> includedTypes, List<ProductType> excludedTypes, Period loyaltyYears) {
        super(name, level, priority, isCombinedOnLevel, percent, amount, userType, includedTypes, excludedTypes);
        this.loyaltyYears = loyaltyYears;
    }

    @Override
    public BigDecimal apply(Bill bill) {
        final User customer = bill.getCustomer();
        if(!customer.getTypes().contains(super.getUserType())
                || customer.getCreatedDate().plus(loyaltyYears).isAfter(LocalDate.now())){
            return BigDecimal.ZERO;
        }
        BigDecimal discountAmount = BigDecimal.ZERO;
        for (Item item : bill.getItems()) {
            final ProductType productType = item.getProduct().getType();
            if((super.getIncludedTypes().isEmpty() || super.getIncludedTypes().contains(productType)) && !super.getExcludedTypes().contains(productType)) {
                final BigDecimal discountByCurrentItem = item.originalAmount()
                        .multiply(super.getPercent())
                        .divide(BigDecimal.valueOf(100))
                        .add(super.getAmount());
                discountAmount = discountAmount.add(discountByCurrentItem);
            }
        }
        return discountAmount;
    }
}
