package com.diconiumwvv.storesservice.products;

import com.diconiumwvv.storesservice.exceptions.SbhException;
import com.diconiumwvv.storesservice.products.dtos.ProductDTO;
import com.diconiumwvv.storesservice.products.dtos.ProductDraftDTO;
import io.sphere.sdk.client.BlockingSphereClient;
import io.sphere.sdk.products.Product;
import io.sphere.sdk.products.ProductDraft;
import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.products.ProductProjectionType;
import io.sphere.sdk.products.commands.ProductCreateCommand;
import io.sphere.sdk.products.commands.ProductUpdateCommand;
import io.sphere.sdk.products.commands.updateactions.Publish;
import io.sphere.sdk.products.queries.ProductProjectionByIdGet;
import io.sphere.sdk.products.queries.ProductProjectionQuery;
import io.sphere.sdk.queries.PagedQueryResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Slf4j
@Service
public class ProductsService {

    @Resource
    private BlockingSphereClient client;

    @Resource
    private ConversionService conversionService;


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

    public ProductDTO createProduct(final ProductDraftDTO productDraftDTO) throws SbhException, ExecutionException, InterruptedException {
        log.info("About to create a new product {}", productDraftDTO.getName());

        ProductDraft productDraft = conversionService.convert(productDraftDTO, ProductDraft.class);
        if (productDraft == null) {
            throw new SbhException("Product draft could not be converted.");
        }

        Product product = client.execute(ProductCreateCommand.of(productDraft)).toCompletableFuture().get();
        Product publishedProduct = client.execute(ProductUpdateCommand.of(product, Publish.of())).toCompletableFuture().get();

        // TODO: combine product with store
        return conversionService.convert(publishedProduct.toProjection(ProductProjectionType.CURRENT), ProductDTO.class);
    }
}
