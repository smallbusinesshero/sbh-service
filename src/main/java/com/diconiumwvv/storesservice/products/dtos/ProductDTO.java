package com.diconiumwvv.storesservice.products.dtos;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

import java.util.List;
import java.util.Locale;
import java.util.Map;

@Data
@Builder
public class ProductDTO {

    private String id;
    private Map<Locale, String> name;
    private Map<Locale, String> description;
    private List<String> categories;
    private List<String> categoryOrderHints;
    private Map<Locale, String> slug;
    private Map<Locale, String> metaTitle;
    private Map<Locale, String> metaDescription;
    private ProductVariantDTO masterVariant;
    private List<ProductVariantDTO> variants;

    @Tolerate
    public ProductDTO() {
        // Required for Jackson
    }
}
