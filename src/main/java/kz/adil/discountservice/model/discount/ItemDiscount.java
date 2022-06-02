package kz.adil.discountservice.model.discount;

import kz.adil.discountservice.model.bill.Bill;
import kz.adil.discountservice.model.bill.Item;
import kz.adil.discountservice.model.bill.ProductType;
import kz.adil.discountservice.model.user.UserType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class ItemDiscount extends AbstractDiscount {

    private List<ProductType> includedTypes = new ArrayList<>();
    private List<ProductType> excludedTypes = new ArrayList<>();
    private UserType userType;

    public ItemDiscount(String name, int level, int priority, boolean isCombinedOnLevel, BigDecimal percent, BigDecimal amount,
                        UserType userType, List<ProductType> includedTypes, List<ProductType> excludedTypes) {
        super(name, level, priority, isCombinedOnLevel, percent, amount);
        this.includedTypes.addAll(includedTypes);
        this.excludedTypes.addAll(excludedTypes);
        this.userType = userType;
    }

    @Override
    public BigDecimal apply(Bill bill) {
        if (!bill.getCustomer().getTypes().contains(userType)) {
            return BigDecimal.ZERO;
        }
        BigDecimal discountAmount = BigDecimal.ZERO;
        for (Item item : bill.getItems()) {
            final ProductType productType = item.getProduct().getType();
            if ((includedTypes.isEmpty() || includedTypes.contains(productType)) && !excludedTypes.contains(productType)) {
                final BigDecimal discountByCurrentItem = item.originalAmount()
                        .multiply(super.getPercent())
                        .divide(BigDecimal.valueOf(100), RoundingMode.UNNECESSARY)
                        .add(super.getAmount());
                discountAmount = discountAmount.add(discountByCurrentItem);
            }
        }
        return discountAmount;
    }
}
