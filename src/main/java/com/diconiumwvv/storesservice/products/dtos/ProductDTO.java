package com.diconiumwvv.storesservice.products.dtos;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.sphere.sdk.models.LocalizedString;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

@Data
@Builder
@JsonPropertyOrder({"id", "name", "description", "slug", "price", "masterVariant"})
public class ProductDTO {

    private String id;
    private LocalizedString name;
    private LocalizedString description;
    private LocalizedString slug;
    private PriceDTO price;
    private ProductVariantDTO masterVariant;

    @Tolerate
    public ProductDTO() {
        // Required for Jackson
    }
}
