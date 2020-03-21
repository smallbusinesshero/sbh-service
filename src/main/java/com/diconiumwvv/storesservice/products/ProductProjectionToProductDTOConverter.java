package com.diconiumwvv.storesservice.products;

import com.diconiumwvv.storesservice.products.dtos.ProductDTO;
import io.sphere.sdk.products.ProductProjection;
import org.springframework.core.convert.converter.Converter;

public class ProductProjectionToProductDTOConverter implements Converter<ProductProjection, ProductDTO> {

    @Override
    public ProductDTO convert(ProductProjection product) {

        return ProductDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .categories(product.getCategories())
                .categoryOrderHints(product.getCategoryOrderHints())
                .slug(product.getSlug())
                .metaTitle(product.getMetaTitle())
                .metaDescription(product.getMetaDescription())
                .masterVariant(product.getMasterVariant())
                .variants(product.getVariants())
                .build();
    }
}
