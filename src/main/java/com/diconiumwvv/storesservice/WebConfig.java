package com.diconiumwvv.storesservice;

import com.diconiumwvv.storesservice.products.ProductDraftDTOToProductDraftConverter;
import com.diconiumwvv.storesservice.products.ProductProjectionToProductDTOConverter;
import com.diconiumwvv.storesservice.stores.dtos.StoreDraftDTOToStoreDraftConverter;
import com.diconiumwvv.storesservice.stores.dtos.StoreToStoreDTOConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new ProductProjectionToProductDTOConverter());
        registry.addConverter(new ProductDraftDTOToProductDraftConverter());
        registry.addConverter(new StoreToStoreDTOConverter());
        registry.addConverter(new StoreDraftDTOToStoreDraftConverter());
    }
}
