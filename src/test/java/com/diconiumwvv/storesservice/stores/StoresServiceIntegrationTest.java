package com.diconiumwvv.storesservice.stores;

import com.diconiumwvv.storesservice.exceptions.SbhException;
import com.diconiumwvv.storesservice.stores.dtos.StoreDTO;
import com.diconiumwvv.storesservice.stores.dtos.StoreDraftDTO;
import com.neovisionaries.i18n.CountryCode;
import io.sphere.sdk.models.Address;
import io.sphere.sdk.models.GeoJSON;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.models.Point;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.internal.bytebuddy.utility.RandomString;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest
@ActiveProfiles({"test", "googleapi"})
class StoresServiceIntegrationTest {

    public static String TEST_KEY;
    public static final double LONGITUDE = 10.42932;
    public static final double LATITUDE = 50.48528;
    private static final GeoJSON GEO_LOCATION = Point.of(LONGITUDE, LATITUDE);

    @Autowired
    private StoresService storesService;

    private static final String TEST_STORE_NAME = "Java Integration Test Store " + LocalDateTime
        .now();
    public static final LocalizedString LOCALIZED_STORE_NAME =
        LocalizedString.of(Locale.GERMANY, TEST_STORE_NAME);

    @BeforeEach void setUp() {
        TEST_KEY = RandomString.make(8);
    }

    @Test void getStoreForID() {
    }

    @Test void getStoresByNeighborhood() {
    }

    @Test void getAllStores() {
    }

    @Test void retrieveNearbyStoresFromCommerceTools() {
    }

    @Test void searchStore() {
    }

    @Test
    void createStore() throws ExecutionException, InterruptedException, SbhException, IOException {
        // TODO adapt tests
        MultipartFile shopOwnerImage = null;
        MultipartFile profileImageURL = null;
        final StoreDraftDTO storeDraft = getStoreDraftDTO();
        storeDraft.setGeoLocation(GEO_LOCATION);
        StoreDTO createdStore = storesService.createStore(storeDraft, shopOwnerImage, profileImageURL);
        assertThat(createdStore).isNotNull();
        assertThat(createdStore.getGeoLocation()).isNotNull();
        assertThat(((Point) createdStore.getGeoLocation()).getLatitude()).isGreaterThan(40);
        assertThat(((Point) createdStore.getGeoLocation()).getLongitude()).isLessThan(20);
        log.debug(createdStore.getGeoLocation().toString());
    }

    @Test
    void createStoreWithPrefilledGeolocation() throws ExecutionException, InterruptedException, SbhException, IOException {
        // TODO adapt tests
        MultipartFile shopOwnerImage = null;
        MultipartFile profileImageURL = null;
        final StoreDraftDTO storeDraft = getStoreDraftDTO();
        storeDraft.setGeoLocation(GEO_LOCATION);
        StoreDTO createdStore = storesService.createStore(storeDraft, shopOwnerImage, profileImageURL);
        assertThat(createdStore).isNotNull();
        assertThat(createdStore.getGeoLocation()).isNotNull();
        assertThat(((Point) createdStore.getGeoLocation()).getLatitude()).isEqualTo(LATITUDE);
        assertThat(((Point) createdStore.getGeoLocation()).getLongitude()).isEqualTo(LONGITUDE);
        log.debug(createdStore.getGeoLocation().toString());
    }

    private StoreDraftDTO getStoreDraftDTO() {
        final StoreDraftDTO storeDraft = new StoreDraftDTO();
        storeDraft.setName(LOCALIZED_STORE_NAME);
        storeDraft.setAddress(createTestAddress());
        storeDraft.setKey(TEST_KEY);
        return storeDraft;
    }

    private Address createTestAddress() {
        return Address.of(CountryCode.DE)
            .withCity("Berlin")
            .withStreetName("Donaustraße")
            .withPostalCode("12045")
            .withStreetNumber("1");
    }
}
