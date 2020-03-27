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
     *
     * @param longitude
     * @param latitude
     * @param radius radius in meter
     * @return List of stores in range
     */
    public List<StoreDTO> searchStoreByLatLng(double longitude, double latitude, double radius) {
        Point point = Point.of(longitude, latitude);
        // Radius in meter
        ChannelQuery channelQuery = ChannelQuery.of().withPredicates(m -> m.geoLocation().withinCircle(point, radius));
        List<Channel> results = client.executeBlocking(channelQuery).getResults();
        return results.stream()
                .map(channel -> conversionService.convert(channel, StoreDTO.class))
                .collect(Collectors.toList());
    }

    public List<StoreDTO> searchForStore(StoreSearchDTO storeSearchDTO) {

        return Arrays.stream(geoService.geocode(storeSearchDTO.getAddress()))
                .findFirst()
                .map(geocodingResult -> {
                    LatLng location = geocodingResult.geometry.location;
                    return searchStoreByLatLng(location.lng, location.lat, storeSearchDTO.getRadius());
                }).orElse(Collections.emptyList());
    }
}
