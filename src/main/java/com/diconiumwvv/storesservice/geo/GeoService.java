package com.diconiumwvv.storesservice.geo;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.GeocodingResult;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class GeoService {

    @Resource
    private GeoApiContext geoApiContext;

    public GeocodingResult[] geocode(String address) {
        log.info("About to retrieve address information from GoogleAPI.");
        GeocodingResult[] results = new GeocodingResult[0];
        try {
            results = GeocodingApi.geocode(geoApiContext, address).
                language("de").
                region("DE").
                await();
        } catch (Exception e) {
            log.error("Could not retrieve information from GoogleAPI because "
                + "of an error, returning empty result list: ", e);
        }
        return results;
    }
}
