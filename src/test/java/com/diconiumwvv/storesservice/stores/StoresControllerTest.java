package com.diconiumwvv.storesservice.stores;

import com.diconiumwvv.storesservice.exceptions.SbhException;
import com.diconiumwvv.storesservice.stores.dtos.StoreDTO;
import com.diconiumwvv.storesservice.stores.dtos.StoreDraftDTO;
import io.sphere.sdk.models.LocalizedString;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestClientException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@Slf4j
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class StoresControllerTest {

    @LocalServerPort private int port;
    private final String storesEndpoint = "/stores/";
    @Value("${auth.basic.user}") private String username;
    @Value("${auth.basic.password}") private String password;

    private static final String WRONG_PASSWORD = "xxxxxxxx";
    public static final String TEST_STORE_NAME = "Silber";
    public static final LocalizedString LOCALIZED_STORE_NAME =
        LocalizedString.of(Locale.GERMANY, TEST_STORE_NAME);

    private StoresController storesController;

    @MockBean
    private StoresService storesService;

    @Autowired
    private TestRestTemplate restTemplate;
    private HttpHeaders headers = new HttpHeaders();

    private StoreDTO store;
    private List<StoreDTO> stores;

    @BeforeEach void setUp() {
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        stores = setUpMockStores();
    }

    private List<StoreDTO> setUpMockStores() {
        final StoreDTO store = new StoreDTO();
        store.setName(LOCALIZED_STORE_NAME);
        List<StoreDTO> stores = new ArrayList<>();
        stores.add(store);
        return stores;
    }

    @Test void getStores() throws ExecutionException, InterruptedException {
        given(storesService.searchStore(any())).willReturn(stores);

        HttpEntity request = new HttpEntity(headers);
        ResponseEntity<StoreDTO[]> responseEntity = restTemplate
            .withBasicAuth(username, password)
            .exchange(storesEndpoint, HttpMethod.GET, request, StoreDTO[].class);

        System.out.println(responseEntity);
        assertThat(responseEntity.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(responseEntity.getBody()).isNotNull();
        assertThat(responseEntity.getBody()).hasSameSizeAs(stores);
    }

    @Test void wrongCredentials() {
        assertThrows(RestClientException.class, () -> restTemplate
            .withBasicAuth(username, WRONG_PASSWORD)
            .getForEntity(storesEndpoint, StoreDTO.class));
    }

    @Test void createStore() throws ExecutionException, InterruptedException, SbhException, IOException {
        // TODO adapt tests
        MultipartFile shopOwnerImage = null;
        MultipartFile profileImageURL = null;
        given(storesService.createStore(any(), shopOwnerImage, profileImageURL)).willReturn(store);

        StoreDraftDTO storeDraft = new StoreDraftDTO();
        storeDraft.setName(LOCALIZED_STORE_NAME);
        HttpEntity<StoreDraftDTO> request = new HttpEntity<>(storeDraft, headers);

        ResponseEntity<StoreDTO> responseEntity = restTemplate
            .withBasicAuth(username, password)
            .postForEntity(storesEndpoint, request, StoreDTO.class);

        assertThat(responseEntity.getStatusCode().is2xxSuccessful()).isTrue();
    }
}
