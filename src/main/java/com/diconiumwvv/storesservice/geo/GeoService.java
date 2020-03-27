package com.diconiumwvv.storesservice.geo;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.GeocodingResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;

@Service

public class GeoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(GeoService.class);
    @Resource
    private GeoApiContext geoApiContext;

    public GeocodingResult[] geocode(String address) {
        GeocodingResult[] results = new GeocodingResult[0];
        try {
            results = GeocodingApi.geocode(geoApiContext, address).await();
        } catch (ApiException | InterruptedException| IOException e) {
           LOGGER.error("Error while using google geocoding api", e);
        }
        return results;
    }
}
