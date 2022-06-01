package kz.adil.discountservice.service.impl;

import kz.adil.discountservice.model.discount.AbstractDiscount;
import kz.adil.discountservice.model.Bill;
import kz.adil.discountservice.model.dto.DiscountInfo;
import kz.adil.discountservice.repository.DiscountRepository;
import kz.adil.discountservice.service.DiscountService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
@RequiredArgsConstructor
public class DiscountServiceImpl implements DiscountService {
    private final DiscountRepository discountRepository;

    @Override
    public DiscountInfo calculateDiscount(Bill bill) {
        final List<AbstractDiscount> discounts = discountRepository.findAll(Sort.by(Sort.Order.by("level"), Sort.Order.by("priority")));
        Set<Integer> noncombinedLevelsApplied = new HashSet<>();
        Map<Integer, BigDecimal> levelDiscounts = new HashMap<>();
        for (AbstractDiscount discount : discounts) {
            if (!discount.isCombinedOnLevel() && noncombinedLevelsApplied.contains(discount.getLevel())) {
                continue;
            }
            if (discount.isCombinedOnLevel() || !noncombinedLevelsApplied.contains(discount.getLevel())) {
                final BigDecimal discountAmount = discount.apply(bill);
                if (!discount.isCombinedOnLevel() && discountAmount.compareTo(BigDecimal.ZERO)>0) {
                    noncombinedLevelsApplied.add(discount.getLevel());
                }
                final BigDecimal levelDiscount = levelDiscounts.getOrDefault(discount.getLevel(), BigDecimal.ZERO);
                levelDiscounts.put(discount.getLevel(), levelDiscount.add(discountAmount));
            }
        }
        final BigDecimal totalDiscount = levelDiscounts.values().stream().reduce(BigDecimal.ZERO, BigDecimal::add);
        DiscountInfo discountInfo = new DiscountInfo();
        discountInfo.setDiscountAmount(totalDiscount);

        return discountInfo;
    }
}
