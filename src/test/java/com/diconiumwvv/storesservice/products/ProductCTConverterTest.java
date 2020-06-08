package com.diconiumwvv.storesservice.products;

import com.diconiumwvv.storesservice.products.dtos.PriceDTO;
import com.diconiumwvv.storesservice.products.dtos.ProductDraftDTO;
import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.json.SphereJsonUtils;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.products.ProductDraft;
import io.sphere.sdk.products.ProductVariantDraft;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;

public class ProductCTConverterTest {

    private static final String DEFAULT_PRODUCT_KEY = "Default-Product-Key";
    private static final Locale TEST_LOCALE = Locale.GERMANY;

    private static final String PRODUCT_NAME = "Product Name";
    private static final String PRODUCT_DESCRIPTION = "Product Description";
    private static final String STORE_KEY = "test-channel-key";
    private static final String STORE_ID = "test-channel-id";

    private final ProductCTConverter productCTConverter = new ProductCTConverter();

    private Channel testStore;

    @BeforeEach
    void setUp() {
        testStore = setUpTestStore();
    }

    private Channel setUpTestStore() {

        return SphereJsonUtils.readObject("{\n" +
                "  \"id\": \"" + STORE_ID + "\",\n" +
                "  \"key\": \"" + STORE_KEY + "\"\n" +
                "}", Channel.typeReference());
    }

    private ProductDraftDTO buildProductDraft(String productName, String productDescription) {
        return ProductDraftDTO.builder()
                .name(LocalizedString.of(TEST_LOCALE, productName))
                .description(LocalizedString.of(TEST_LOCALE, productDescription))
                .price(PriceDTO.builder().value(BigDecimal.TEN).currencyCode("EUR").build())
                .build();
    }

    @Test
    void convertProductDraft() {
        final ProductDraftDTO testDraft = buildProductDraft(PRODUCT_NAME, PRODUCT_DESCRIPTION);

        ProductDraft convertedProductDraft = productCTConverter.convert(testDraft, testStore, DEFAULT_PRODUCT_KEY);
        ProductVariantDraft masterVariant = convertedProductDraft.getMasterVariant();

        assertThat(convertedProductDraft).isNotNull();
        assertThat(convertedProductDraft.getProductType().getKey()).isEqualTo(DEFAULT_PRODUCT_KEY);
        assertThat(convertedProductDraft.getSlug().get(TEST_LOCALE)).contains(STORE_KEY);

        assertThat(masterVariant).isNotNull();
        assertThat(masterVariant.getPrices()).isNotNull();
        assertThat(masterVariant.getPrices().get(0)).isNotNull();
        assertThat(masterVariant.getPrices().get(0).getChannel()).isNotNull();
        assertThat(masterVariant.getPrices().get(0).getChannel().getId()).isEqualTo(STORE_ID);


    }

    @Test
    void normalizeNameForSlug() {
        final ProductDraftDTO testDraft = buildProductDraft("name%with+special?CHARACTERS&", PRODUCT_DESCRIPTION);
        ProductDraft convertedProductDraft = productCTConverter.convert(testDraft, testStore, DEFAULT_PRODUCT_KEY);

        // test for product name normalization
        String createdSlug = convertedProductDraft.getSlug().get(TEST_LOCALE);
        assertThat(createdSlug).doesNotContain("%");
        assertThat(createdSlug).doesNotContain("+");
        assertThat(createdSlug).doesNotContain("?");
        assertThat(createdSlug).doesNotContain("&");
        assertThat(createdSlug).contains("special");
        assertThat(createdSlug).contains("characters");
        assertThat(createdSlug).contains(STORE_KEY.toLowerCase());
    }
}
