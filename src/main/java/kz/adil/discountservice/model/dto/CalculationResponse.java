package kz.adil.discountservice.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;

@Getter
@Setter
@Builder
public class CalculationResponse {
    private ZonedDateTime createdDatetime;
    private String discountAmount;
    private String totalAmount;
    private String netAmount;
}
