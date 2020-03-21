package com.diconiumwvv.storesservice.products.dtos;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

import java.util.List;

@Data
@Builder
public class ProductVariantDTO {

    private String id;
    private String sku;
    private List<PriceDTO> prices;
    private List<ImageDTO> images;
    private List<AttributesDTO> attributes;
    private List<AssetsDTO> assets;

    @Tolerate
    public ProductVariantDTO() {
        // Required for Jackson
    }
}
