package com.diconiumwvv.storesservice.products.dtos;

import io.sphere.sdk.models.LocalizedString;
import lombok.Data;

@Data
public class ProductDraftDTO {

    private LocalizedString description;
    private LocalizedString name;
    private PriceDTO price;

}
