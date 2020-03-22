package com.diconiumwvv.storesservice.products.dtos;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class PriceDTO {
    private BigDecimal value;
    private String currencyCode;
}
