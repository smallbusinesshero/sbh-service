package com.diconiumwvv.storesservice.products.dtos;

import io.sphere.sdk.products.Image;
import io.sphere.sdk.products.attributes.Attribute;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

import java.util.List;

@Data
@Builder
public class ProductVariantDTO {

    private Integer id;
    private String sku;
    private List<Image> images;
    private List<Attribute> attributes;

    @Tolerate
    public ProductVariantDTO() {
        // Required for Jackson
    }
}
