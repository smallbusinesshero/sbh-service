package com.diconiumwvv.storesservice.stores;

import com.diconiumwvv.storesservice.stores.dtos.StoreDTO;
import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.channels.queries.ChannelByIdGet;
import io.sphere.sdk.channels.queries.ChannelQuery;
import io.sphere.sdk.channels.queries.ChannelQueryBuilder;
import io.sphere.sdk.client.BlockingSphereClient;
import io.sphere.sdk.queries.PagedQueryResult;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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

    public Channel getStoreForID(String channelId) throws ExecutionException, InterruptedException {
        CompletionStage<Channel> future = client.execute(ChannelByIdGet.of(channelId));
        return future.toCompletableFuture().get();
    }

    public List<StoreDTO> getStoresByNeighborhood(String neighborhood) throws ExecutionException, InterruptedException {
        PagedQueryResult<Channel> channelPagedQueryResult1 = client.execute(ChannelQueryBuilder.of().build()).toCompletableFuture().get();
        List<StoreDTO> collect = channelPagedQueryResult1.getResults().stream()
                .map(channel -> conversionService.convert(channel, StoreDTO.class))
                .filter(Objects::nonNull)
                .filter(store -> store.getNeighborhood() != null)
                .filter(store -> store.getNeighborhood().contains(neighborhood))
                .collect(Collectors.toList());

        return collect;
    }
}
