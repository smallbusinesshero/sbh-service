package com.diconiumwvv.storesservice.products;

import com.diconiumwvv.storesservice.products.dtos.ProductDraftDTO;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.models.ResourceIdentifier;
import io.sphere.sdk.products.ProductDraft;
import io.sphere.sdk.products.ProductDraftBuilder;
import io.sphere.sdk.products.ProductVariantDraftBuilder;
import org.springframework.core.convert.converter.Converter;

import java.util.Locale;

public class ProductDraftDTOToProductDraftConverter implements Converter<ProductDraftDTO, ProductDraft> {

    @Override
    public ProductDraft convert(ProductDraftDTO productDraftDTO) {
        // TODO: Build SLUG from product name and channel-id
        return ProductDraftBuilder.of(
                ResourceIdentifier.ofKey("default-product-type"),
                productDraftDTO.getName(),
                LocalizedString.of(Locale.GERMANY, "totally-unique-slug-i-swear"),
                ProductVariantDraftBuilder.of().build()
        ).build();
    }
}
