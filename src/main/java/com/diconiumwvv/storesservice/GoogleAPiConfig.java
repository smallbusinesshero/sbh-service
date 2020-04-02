package com.diconiumwvv.storesservice;

import com.google.maps.GeoApiContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GoogleAPiConfig {

    @Bean
    public GeoApiContext geoApiContext (@Value("${google.apikey}") String apiKey) {
        return new GeoApiContext.Builder()
                .apiKey(apiKey)
                .build();
    }
}
