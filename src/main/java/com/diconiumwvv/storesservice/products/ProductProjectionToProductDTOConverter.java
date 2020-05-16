package com.diconiumwvv.storesservice.products;

import com.diconiumwvv.storesservice.products.dtos.PriceDTO;
import com.diconiumwvv.storesservice.products.dtos.ProductDTO;
import com.diconiumwvv.storesservice.products.dtos.ProductVariantDTO;
import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.products.ProductVariant;
import org.springframework.core.convert.converter.Converter;

import javax.money.NumberValue;
import java.math.BigDecimal;

public class ProductProjectionToProductDTOConverter implements Converter<ProductProjection, ProductDTO> {

    private ProductVariantDTO convertVariant(ProductVariant productVariant) {

        return ProductVariantDTO.builder()
                .id(productVariant.getId())
                .sku(productVariant.getSku())
                .images(productVariant.getImages())
                .attributes(productVariant.getAttributes())
                .build();
    }

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
                .slug(product.getSlug())
                .masterVariant(convertVariant(product.getMasterVariant()))
                .build();
    }
}
