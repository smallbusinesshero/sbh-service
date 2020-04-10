package com.diconiumwvv.storesservice.stores;

import com.diconiumwvv.storesservice.geo.GeoService;
import com.diconiumwvv.storesservice.stores.dtos.StoreDTO;
import com.diconiumwvv.storesservice.stores.dtos.StoreSearchDTO;
import com.google.maps.model.LatLng;
import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.channels.queries.ChannelByIdGet;
import io.sphere.sdk.channels.queries.ChannelQuery;
import io.sphere.sdk.channels.queries.ChannelQueryBuilder;
import io.sphere.sdk.client.BlockingSphereClient;
import io.sphere.sdk.models.Point;
import io.sphere.sdk.queries.PagedQueryResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Slf4j
@Service
public class StoresService {
    @Resource
    private BlockingSphereClient client;

    @Resource
    private ConversionService conversionService;

    @Resource
    private GeoService geoService;

    public Channel getStoreForID(String channelId) throws ExecutionException, InterruptedException {
        CompletionStage<Channel> future = client.execute(ChannelByIdGet.of(channelId));
        return future.toCompletableFuture().get();
    }

    public List<StoreDTO> getStoresByNeighborhood(String neighborhood) throws ExecutionException, InterruptedException {
        PagedQueryResult<Channel> channelPagedQueryResult1 = client.execute(ChannelQueryBuilder.of().build()).toCompletableFuture().get();

        return channelPagedQueryResult1.getResults().stream()
                .map(channel -> conversionService.convert(channel, StoreDTO.class))
                .filter(Objects::nonNull)
                .filter(store -> store.getNeighborhood() != null)
                .filter(store -> store.getNeighborhood().contains(neighborhood))
                .collect(Collectors.toList());
    }

    public List<StoreDTO> getAllStores() throws ExecutionException, InterruptedException {
        PagedQueryResult<Channel> channelPagedQueryResult1 = client.execute(ChannelQueryBuilder.of().build()).toCompletableFuture().get();
        return channelPagedQueryResult1.getResults().stream()
                .map(channel -> conversionService.convert(channel, StoreDTO.class))
                .collect(Collectors.toList());
    }

    /**
     * @param longitude the address' longitude
     * @param latitude the address' latitude
     * @param radius radius in meters
     * @return List of stores within the given radius around the geolocation
     */
    public List<StoreDTO> searchStoreByLatLng(double longitude, double latitude, double radius) {
        log.info("Searching commerce tools for stores around {}, {}...", latitude, longitude);
        Point point = Point.of(longitude, latitude);
        // Radius in meter
        ChannelQuery channelQuery = ChannelQuery.of().withPredicates(m -> m.geoLocation().withinCircle(point, radius));
        List<Channel> results = client.executeBlocking(channelQuery).getResults();
        log.info("Retrieved {} nearby stores from commerce tools.", results.size());
        return results.stream()
                .map(channel -> conversionService.convert(channel, StoreDTO.class))
                .collect(Collectors.toList());
    }

    private List<StoreDTO> searchByLocation(StoreSearchDTO storeSearchDTO) {
        return Arrays.stream(geoService.geocode(storeSearchDTO.getAddress()))
                .findFirst()
                .map(geocodingResult -> {
                    log.info("Picking the first geocoding result from GoogleAPI.");
                    LatLng location = geocodingResult.geometry.location;
                    return searchStoreByLatLng(location.lng, location.lat, storeSearchDTO.getRadius());
                }).orElse(Collections.emptyList());
    }

    public List<StoreDTO> searchStore(String neighborhood) throws ExecutionException, InterruptedException {
        log.info("About to search for stores around {}...", neighborhood);

        if (StringUtils.isBlank(neighborhood)) {
            log.info("Empty search term, returning all stores.");
            return getAllStores();
        }

        StoreSearchDTO storeSearchDTO = new StoreSearchDTO();
        storeSearchDTO.setAddress(neighborhood);
        storeSearchDTO.setRadius(5000.0);
        List<StoreDTO> storeDTOS;

        log.info("Trying to get the place's geolocation, searching for stores around it.");
        storeDTOS = searchByLocation(storeSearchDTO);
        log.info("Retrieved {} stores from geolocation search. ", storeDTOS.size());

        if (storeDTOS.isEmpty()) {
            log.info("Could not find anything using geo location.");
            storeDTOS = getStoresByNeighborhood(neighborhood);
        }

        if (storeDTOS.isEmpty()) {
            log.info("Could not find anything matching the neighborhood exactly, returning all stores.");
            storeDTOS = getAllStores();
        }
        return storeDTOS;
    }
}
