package com.diconiumwvv.storesservice.geo;

import com.diconiumwvv.storesservice.exceptions.SbhException;
import com.google.maps.GeoApiContext;
import io.sphere.sdk.models.Point;
import lombok.extern.slf4j.Slf4j;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@Slf4j
@RunWith(MockitoJUnitRunner.class)
class GeoServiceTest {
    public static String SEARCH_TERM;

    @Mock private GeoApiContext geoApiContext;
    private GeoService geoService = new GeoService(geoApiContext);

    @Ignore("Add thin layer for geocoding API")
    void shouldRetrieveGeoLocation() throws SbhException, InterruptedException {
        SEARCH_TERM = "Nansenstrasse 17, 12047 Berlin";
        Point geoLocation = geoService.retrieveGeoLocation(SEARCH_TERM);
        log.info("Result: {} ", geoLocation);
    }
}
