package com.diconiumwvv.storesservice.stores;

import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.channels.queries.ChannelByIdGet;
import io.sphere.sdk.channels.queries.ChannelQueryBuilder;
import io.sphere.sdk.client.BlockingSphereClient;
import io.sphere.sdk.queries.PagedQueryResult;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;

@Service
public class StoresService {
    @Resource
    private BlockingSphereClient client;

    public Channel getStoreForID(String channelId) throws ExecutionException, InterruptedException {
        CompletionStage<Channel> future = client.execute(ChannelByIdGet.of(channelId));
        return future.toCompletableFuture().get();
    }

    public List<Channel> getStoresByNeighborhood(String neighborhood) throws ExecutionException, InterruptedException {
        CompletionStage<PagedQueryResult<Channel>> future = client.execute(ChannelQueryBuilder.of().build());
        // TODO: actually use neighborhood for filtering
        PagedQueryResult<Channel> channelPagedQueryResult = future.toCompletableFuture().get();
        return channelPagedQueryResult.getResults();
    }
}
