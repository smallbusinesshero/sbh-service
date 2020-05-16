package com.diconiumwvv.storesservice.stores;

import com.diconiumwvv.storesservice.exceptions.SbhException;
import com.diconiumwvv.storesservice.geo.GeoService;
import com.diconiumwvv.storesservice.stores.dtos.StoreDTO;
import com.diconiumwvv.storesservice.stores.dtos.StoreDraftDTO;
import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.channels.ChannelDraft;
import io.sphere.sdk.channels.commands.ChannelCreateCommand;
import io.sphere.sdk.channels.queries.ChannelByIdGet;
import io.sphere.sdk.channels.queries.ChannelQuery;
import io.sphere.sdk.channels.queries.ChannelQueryBuilder;
import io.sphere.sdk.client.BlockingSphereClient;
import io.sphere.sdk.models.Point;
import io.sphere.sdk.queries.PagedQueryResult;
import io.sphere.sdk.types.CustomFields;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Slf4j @Service public class StoresService {
    public static final int RADIUS = 5000;
    @Resource private BlockingSphereClient commerceToolsClient;

    @Resource private ConversionService conversionService;

    @Resource private GeoService geoService;

    public Channel getStoreForID(String channelId) throws ExecutionException, InterruptedException {
        CompletionStage<Channel> future = commerceToolsClient.execute(ChannelByIdGet.of(channelId));
        return future.toCompletableFuture().get();
    }

    public List<StoreDTO> getStoresByNeighborhood(String neighborhood)
        throws ExecutionException, InterruptedException {
        PagedQueryResult<Channel> channelPagedQueryResult1 =
            commerceToolsClient.execute(ChannelQueryBuilder.of().build()).toCompletableFuture()
                .get();

        return channelPagedQueryResult1.getResults().stream()
            .map(channel -> conversionService.convert(channel, StoreDTO.class))
            .filter(Objects::nonNull).filter(store -> store.getNeighborhood() != null)
            .filter(store -> store.getNeighborhood().contains(neighborhood))
            .collect(Collectors.toList());
    }

    private static boolean isChannelPublished(Channel channel) {
        if (channel == null) {
            return false;
        }
        CustomFields customFields = channel.getCustom();
        if (customFields == null) {
            return false;
        }
        Boolean published = customFields.getFieldAsBoolean("published");
        return published != null;
    }

    public List<StoreDTO> getAllStores() throws ExecutionException, InterruptedException {

        PagedQueryResult<Channel> queryResult =
            commerceToolsClient.execute(ChannelQueryBuilder.of().build()).toCompletableFuture()
                .get();
        return queryResult.getResults().stream().filter(StoresService::isChannelPublished)
            .map(channel -> conversionService.convert(channel, StoreDTO.class))
            .collect(Collectors.toList());
    }

    /**
     * @param longitude the address' longitude
     * @param latitude  the address' latitude
     * @param radius    radius in meters
     * @return List of stores within the given radius around the geolocation
     */
    @NonNull public List<StoreDTO> retrieveNearbyStoresFromCommerceTools(double longitude,
        double latitude, double radius) {
        log.info("Searching commerce tools for stores around {}, {}...", latitude, longitude);
        List<Channel> results = getChannelsWithinMetersRadius(longitude, latitude, radius);
        if (null == results || results.isEmpty()) {
            log.info("No nearby stores found.");
            return Collections.emptyList();
        }
        log.info("Retrieved {} nearby stores from commerce tools.", results.size());
        List<StoreDTO> listOfStores =
            results.stream().filter(Objects::nonNull).filter(StoresService::isChannelPublished)
                .map(channel -> conversionService.convert(channel, StoreDTO.class))
                .collect(Collectors.toList());
        if (listOfStores.isEmpty()) {
            log.info("No published stores found nearby.");
        }
        return listOfStores;
    }

    private List<StoreDTO> searchStoresNearby(String searchTerm, double radius)
        throws InterruptedException, SbhException {
        final Point location = geoService.retrieveGeoLocation(searchTerm);
        return retrieveNearbyStoresFromCommerceTools(location.getLongitude(),
            location.getLatitude(), radius);
    }

    private List<Channel> getChannelsWithinMetersRadius(double longitude, double latitude, double radius) {
        Point point = Point.of(longitude, latitude);
        ChannelQuery channelQuery =
            ChannelQuery.of().withPredicates(m -> m.geoLocation().withinCircle(point, radius));
        return commerceToolsClient.executeBlocking(channelQuery).getResults();
    }

    public List<StoreDTO> searchStore(String searchTerm)
        throws ExecutionException, InterruptedException {
        log.info("About to search for stores around {}...", searchTerm);

        if (StringUtils.isBlank(searchTerm)) {
            log.info("Empty search term, returning all stores.");
            return getAllStores();
        }

        List<StoreDTO> storesSearchResult = Collections.emptyList();

        log.info("Trying to get the place's geolocation, searching for stores around it.");
        try {
            storesSearchResult = searchStoresNearby(searchTerm, RADIUS);
        } catch (SbhException e) {
            log.error("Could not retrieve stores from geolocation search.", e);
        }

        if (storesSearchResult.isEmpty()) {
            storesSearchResult = getStoresByNeighborhood(searchTerm);
        }

        if (storesSearchResult.isEmpty()) {
            log.info(
                "Could not find anything matching the neighborhood exactly, returning all stores.");
            storesSearchResult = getAllStores();
        }
        return storesSearchResult;
    }

    public StoreDTO createStore(final StoreDraftDTO newStore)
        throws ExecutionException, InterruptedException, SbhException {
        log.info("About to create new store {}, {}", newStore.getName(), newStore.getGeoLocation());
        StoreDraftDTO newStoreWithGeoLocation;
        if (null == newStore.getGeoLocation()) {
            log.info("Enriching the GeoLocation for new store");
            newStoreWithGeoLocation = enrichStoreWithGeoLocation(newStore);
        } else {
            log.info("Available GeoLocation {} :", newStore.getGeoLocation());
            newStoreWithGeoLocation = newStore;
        }
        ChannelDraft channelDraft =
            conversionService.convert(newStoreWithGeoLocation, ChannelDraft.class);
        if (null == channelDraft) {
            throw new SbhException("Channel draft could not be converted.");
        }
        Channel channel =
            commerceToolsClient.execute(ChannelCreateCommand.of(channelDraft)).toCompletableFuture()
                .get();
        return conversionService.convert(channel, StoreDTO.class);
    }

    private StoreDraftDTO enrichStoreWithGeoLocation(final StoreDraftDTO newStore)
        throws InterruptedException {
        if (null == newStore || null == newStore.getAddress() || null == newStore.getAddress()
                .getStreetName() || null == newStore.getAddress().getStreetNumber() || null == newStore
                .getAddress().getPostalCode() || null == newStore.getAddress().getCity()) {
                log.warn("Address was incomplete, cannot detect geolocation. Please add it manually.");
        } else {
            final String streetName = newStore.getAddress().getStreetName();
            final String streetNumber = newStore.getAddress().getStreetNumber();
            final String postalCode = newStore.getAddress().getPostalCode();
            final String city = newStore.getAddress().getCity();
            final String address = String
                .format("%s %s, %s %s, Deutschland", streetName, streetNumber, postalCode, city);
            final Point geolocation;
            log.info("GeoLocation of address {}", address);
            try {
                geolocation = geoService.retrieveGeoLocation(address);
                newStore.setGeoLocation(geolocation);
            } catch (SbhException e) {
                log.warn(String.format(
                    "Could not retrieve geolocation for %s, please add it manually.", address), e);
            }
        }
        return newStore;
    }
}
