package com.diconiumwvv.storesservice.products;

import io.sphere.sdk.client.BlockingSphereClient;
import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.products.queries.ProductProjectionByIdGet;
import io.sphere.sdk.products.queries.ProductProjectionQuery;
import io.sphere.sdk.queries.PagedQueryResult;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

@Service
public class ProductsService {

    @Resource
    private BlockingSphereClient client;

    public ProductProjection getProductByID(String productId) {
        return client.executeBlocking(ProductProjectionByIdGet.ofCurrent(productId));
    }

    public List<ProductProjection> getProductsByStore(String storeId) {

        ProductProjectionQuery productProjectionQuery = ProductProjectionQuery.ofCurrent()
                .withPredicates(
                        m -> m.masterVariant()
                                .where(
                                        a -> a.prices()
                                                .channel()
                                                .isInIds(Collections.singletonList(storeId))
                                )
                );
        PagedQueryResult<ProductProjection> pagedQueryResult = client.executeBlocking(productProjectionQuery);
        return pagedQueryResult.getResults();
    }
}
