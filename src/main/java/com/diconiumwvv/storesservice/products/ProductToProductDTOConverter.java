package com.diconiumwvv.storesservice.products;

import com.diconiumwvv.storesservice.products.dtos.ProductDTO;
import io.sphere.sdk.products.Product;
import org.springframework.core.convert.converter.Converter;

import java.util.Optional;

public class ProductToProductDTOConverter implements Converter<Product, ProductDTO> {

    @Override
    public ProductDTO convert(Product product) {

        ProductDTO.ProductDTOBuilder builder = ProductDTO.builder();
        builder.id(product.getId());

        Optional.ofNullable(product.getMasterData().getCurrentUnsafe()).ifPresent(productData -> {

            builder.name(productData.getName())
                    .description(productData.getDescription())
                    .categories(productData.getCategories())
                    .categoryOrderHints(productData.getCategoryOrderHints())
                    .slug(productData.getSlug())
                    .metaTitle(productData.getMetaTitle())
                    .metaDescription(productData.getMetaDescription())
                    .masterVariant(productData.getMasterVariant())
                    .variants(productData.getVariants());
        });
        return builder.build();
    }
}
