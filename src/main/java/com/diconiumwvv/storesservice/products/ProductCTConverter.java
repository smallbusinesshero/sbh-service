package com.diconiumwvv.storesservice.products;

import com.diconiumwvv.storesservice.products.dtos.ProductDraftDTO;
import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.models.DefaultCurrencyUnits;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.models.ResourceIdentifier;
import io.sphere.sdk.products.*;
import org.springframework.stereotype.Service;

import java.text.Normalizer;
import java.util.Locale;

@Service
public class ProductCTConverter {

    static Locale userLocale = Locale.GERMANY;

    ProductVariantDraft convertVariantDraftWithPrice(ProductDraftDTO productDraftDTO, Channel store) {
        return ProductVariantDraftBuilder.of()
                .price(
                        PriceDraft.of(
                                productDraftDTO.getPrice().getValue(),
                                DefaultCurrencyUnits.EUR // TODO read currency unit from input?
                        ).withChannel(store)
                )
                .build();
    }


    LocalizedString buildSlug(LocalizedString productName, Channel store) {
        String defaultLocaleName = productName.get(userLocale);
        String defaultLocaleSlug = store.getKey() + "_" + defaultLocaleName;
        String normalizedSlug = normalizeString(defaultLocaleSlug);
        return LocalizedString.of(userLocale, normalizedSlug);
    }

    private String normalizeString(String original) {
        String normalized = Normalizer.normalize(original, Normalizer.Form.NFD);
        normalized = normalized.replaceAll("[^a-zA-Z_\\-]", "");
        return normalized.toLowerCase();
    }

    public ProductDraft convert(ProductDraftDTO productDraftDTO, Channel store, String productTypeKey) {
        return ProductDraftBuilder.of(
                ResourceIdentifier.ofKey(productTypeKey),
                productDraftDTO.getName(),
                buildSlug(productDraftDTO.getName(), store),
                convertVariantDraftWithPrice(productDraftDTO, store)
        ).build();
    }
}
