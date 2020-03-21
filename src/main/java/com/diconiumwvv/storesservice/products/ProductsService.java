package com.diconiumwvv.storesservice.products;

import io.sphere.sdk.client.BlockingSphereClient;
import io.sphere.sdk.products.Product;
import io.sphere.sdk.products.queries.ProductByIdGet;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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
}
