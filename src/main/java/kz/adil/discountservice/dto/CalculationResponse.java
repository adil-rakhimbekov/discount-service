package kz.adil.discountservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;

@Getter
@Setter
@AllArgsConstructor
public class CalculationResponse {
    private ZonedDateTime createdDatetime;
    private String discountAmount;
    private String totalAmount;
    private String netAmount;
}
