package com.diconiumwvv.storesservice.products;

import com.diconiumwvv.storesservice.products.dtos.PriceDTO;
import com.diconiumwvv.storesservice.products.dtos.ProductDTO;
import io.sphere.sdk.products.ProductProjection;
import org.springframework.core.convert.converter.Converter;

import javax.money.NumberValue;
import java.math.BigDecimal;

public class ProductProjectionToProductDTOConverter implements Converter<ProductProjection, ProductDTO> {

    @Override
    public ProductDTO convert(ProductProjection product) {
        ProductDTO.ProductDTOBuilder builder = ProductDTO.builder();

        product.getMasterVariant().getPrices().stream().findFirst().ifPresent(
                price -> {
                    NumberValue number = price.getValue().getNumber();
                    String currencyCode = price.getValue().getCurrency().getCurrencyCode();
                    PriceDTO priceDTO = PriceDTO.builder()
                            .currencyCode(currencyCode)
                            .value(BigDecimal.valueOf(number.doubleValueExact())).build();

                    builder.price(priceDTO);
                }
        );

        return builder.id(product.getId())
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
