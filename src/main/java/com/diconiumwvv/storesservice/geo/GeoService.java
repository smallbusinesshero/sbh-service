package com.diconiumwvv.storesservice.geo;

import com.diconiumwvv.storesservice.exceptions.SbhException;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;
import io.sphere.sdk.models.Point;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.Locale;

@Slf4j
@Service
public class GeoService {

    private GeoApiContext geoApiContext;

    public GeoService(GeoApiContext geoApiContext) {
        this.geoApiContext = geoApiContext;
    }

    public Point retrieveGeoLocation(String searchTerm)
        throws InterruptedException, SbhException {
        log.info("About to retrieve address information from GoogleMaps API...");
        GeocodingResult[] geoCodes = retrieveGeocodingResultForDefaultLocale(searchTerm);
        log.debug("Result from GoogleMaps API for {}: {}", searchTerm, Arrays.toString(geoCodes));
        LatLng latLng = getFirstGeoLocation(geoCodes);
        return Point.of(latLng.lng, latLng.lat);
    }

    private GeocodingResult[] retrieveGeocodingResultForDefaultLocale(String searchTerm)
        throws InterruptedException, SbhException {
        try {
            return GeocodingApi.geocode(geoApiContext, searchTerm).
                language(Locale.getDefault().getLanguage()).
                region(Locale.getDefault().getCountry()).
                await();
        } catch (ApiException | IOException e) {
            throw new SbhException("Could not connect to GoogleMaps API.", e);
        }
    }

    private LatLng getFirstGeoLocation(GeocodingResult[] geoCodes) {
        return geoCodes[0].geometry.location;
    }
}
