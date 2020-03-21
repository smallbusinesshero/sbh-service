package com.diconiumwvv.storesservice.products;

import io.sphere.sdk.client.BlockingSphereClient;
import io.sphere.sdk.products.Product;
import io.sphere.sdk.products.queries.ProductByIdGet;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;

@Service
public class ProductsService {

    @Resource
    private BlockingSphereClient client;

    public Product getProductByID(String productId) throws ExecutionException, InterruptedException {
        CompletionStage<Product> future = client.execute(ProductByIdGet.of(productId));

        return future.toCompletableFuture().get();
    }

    public List<Product> getProductsByStore(String storeId) throws ExecutionException, InterruptedException {
        CompletionStage<Product> future = client.execute(ProductByIdGet.of("885dfe52-c401-42fd-bf8d-80d6b7c80dd2"));
        return Collections.singletonList(future.toCompletableFuture().get());
    }
}
