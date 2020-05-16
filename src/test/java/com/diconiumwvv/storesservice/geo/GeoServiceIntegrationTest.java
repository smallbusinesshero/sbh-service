package com.diconiumwvv.storesservice.geo;

import com.diconiumwvv.storesservice.exceptions.SbhException;
import io.sphere.sdk.models.Point;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest
@ActiveProfiles("local")
class GeoServiceIntegrationTest {

    public static String SEARCH_TERM;

    @Autowired private GeoService geoService;

    @Test void shouldRetrieveGeoLocationForReducedAddress() throws SbhException, InterruptedException {
        /*SEARCH_TERM = "Donaustraße, Berlin";
        Point geoLocation = geoService.retrieveGeoLocation(SEARCH_TERM);
        log.info("Result: {} ", geoLocation);
        assertThat(geoLocation).isNotNull();
        assertThat(geoLocation.getLatitude()).isGreaterThan(40);
        assertThat(geoLocation.getLongitude()).isLessThan(20);*/
    }

    @Test void shouldRetrieveGeoLocationForPostalCode() throws SbhException, InterruptedException {
        /*SEARCH_TERM = "88400";
        Point geoLocation = geoService.retrieveGeoLocation(SEARCH_TERM);
        log.info("Result: {} ", geoLocation);
        assertThat(geoLocation).isNotNull();
        assertThat(geoLocation.getLatitude()).isGreaterThan(40);
        assertThat(geoLocation.getLongitude()).isLessThan(20);*/
    }
}
