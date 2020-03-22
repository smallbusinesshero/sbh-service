package com.diconiumwvv.storesservice.products.dtos;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.sphere.sdk.categories.Category;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.products.CategoryOrderHints;
import io.sphere.sdk.products.Price;
import io.sphere.sdk.products.ProductVariant;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

import java.util.List;
import java.util.Set;

@Data
@Builder
@JsonPropertyOrder({"id", "name", "description", "categories", "categoryOrderHints", "slug", "metaTitle", "metaDescription", "price", "masterVariant", "variants"})
public class ProductDTO {

    private String id;
    private LocalizedString name;
    private LocalizedString description;
    private Set<Reference<Category>> categories;
    private CategoryOrderHints categoryOrderHints;
    private LocalizedString slug;
    private LocalizedString metaTitle;
    private LocalizedString metaDescription;
    private PriceDTO price;
    private ProductVariant masterVariant;
    private List<ProductVariant> variants;

    @Tolerate
    public ProductDTO() {
        // Required for Jackson
    }
}
