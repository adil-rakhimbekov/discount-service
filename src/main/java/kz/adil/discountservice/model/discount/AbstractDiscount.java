package kz.adil.discountservice.model.discount;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Document(collection = "discounts")
@Data
@AllArgsConstructor
public abstract class AbstractDiscount implements Discount {
    @Id
    private String name;
    private int level;
    private int priority;
    private boolean isCombinedOnLevel;
    private BigDecimal percent;
    private BigDecimal amount;
}

