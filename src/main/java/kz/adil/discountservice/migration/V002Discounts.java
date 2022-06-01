package kz.adil.discountservice.migration;

import io.mongock.api.annotations.ChangeUnit;
import io.mongock.api.annotations.Execution;
import io.mongock.api.annotations.RollbackExecution;
import kz.adil.discountservice.model.*;
import kz.adil.discountservice.model.discount.AbstractDiscount;
import kz.adil.discountservice.model.discount.BillDiscount;
import kz.adil.discountservice.model.discount.ItemDiscount;
import kz.adil.discountservice.model.discount.LoyaltyDiscount;
import kz.adil.discountservice.repository.DiscountRepository;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

import static kz.adil.discountservice.model.ProductType.*;

@RequiredArgsConstructor
@ChangeUnit(id = "discount_groups", order = "002", author = "a.rakhimbekov")
public class V002Discounts {

    private final DiscountRepository discountRepository;

    @Execution
    public void execute(){
        final List<AbstractDiscount> discounts = new ArrayList<>();

        discounts.add(new ItemDiscount("employee_discount",
                1,
                1,
                false,
                BigDecimal.valueOf(30),
                BigDecimal.ZERO,
                UserType.EMPLOYEE,
                new ArrayList<>(),
                List.of(new ProductType(GROCERY))));
        discounts.add(new ItemDiscount("affiliate_discount",
                1,
                2,
                false,
                BigDecimal.valueOf(10),
                BigDecimal.ZERO,
                UserType.AFFILIATE,
                new ArrayList<>(),
                List.of(new ProductType(GROCERY))));
        discounts.add(new LoyaltyDiscount("loyalty_discount",
                1,
                3,
                false,
                BigDecimal.valueOf(5),
                BigDecimal.ZERO,
                UserType.CUSTOMER,
                new ArrayList<>(),
                List.of(new ProductType(GROCERY)),
                Period.ofYears(2)));
        discounts.add(new BillDiscount("bill_discount",
                2,
                1,
                true,
                BigDecimal.valueOf(5),
                BigDecimal.ZERO,
                BigDecimal.valueOf(100)));
        discountRepository.saveAll(discounts);
    }

    @RollbackExecution
    public void rollback() {
        discountRepository.deleteAll();
    }

}
