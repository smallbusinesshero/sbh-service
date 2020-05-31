package com.diconiumwvv.storesservice.products;

import com.diconiumwvv.storesservice.exceptions.SbhException;
import com.diconiumwvv.storesservice.products.dtos.ProductDTO;
import com.diconiumwvv.storesservice.products.dtos.ProductDraftDTO;
import io.sphere.sdk.products.ProductProjection;
import io.swagger.annotations.*;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@CrossOrigin(origins = {
        "http://localhost:3000",
        "https://dds-wvv-frontend.herokuapp.com",
        "https://dev-dds-wvv-frontend.herokuapp.com",
        "https://feature-storefront.herokuapp.com",
        "http://smallbusinesshero.de",
        "http://www.smallbusinesshero.de"
})
@RestController
@Api(tags = "Products API")
public class ProductsController {

    @Resource
    private ConversionService conversionService;

    @Resource
    private ProductsService productsService;

    @ApiOperation(value = "get all products for one specific store")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "ok"),
            @ApiResponse(code = 500, message = "An unexpected error occurred")
    })
    @GetMapping(value = "/products/", produces = "application/json")
    public List<ProductDTO> getProductsByStoreId(@ApiParam(value = "storeId", required = true, example = "ddf24dc6-1a2d-4391-8f34-c5c322b21c1e") @RequestParam String storeId) {
        List<ProductProjection> productsByStore = productsService.getProductsByStore(storeId);
        return productsByStore.stream()
                .map(product -> conversionService.convert(product, ProductDTO.class))
                .collect(Collectors.toList());
    }

    @ApiOperation(value = "get one specific product by ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "ok"),
            @ApiResponse(code = 500, message = "An unexpected error occurred")
    })
    @GetMapping(value = "/products/{id}", produces = "application/json")
    public ProductDTO getProductById(@ApiParam(value = "id", required = true, example = "885dfe52-c401-42fd-bf8d-80d6b7c80dd2") @PathVariable String id) {
        ProductProjection productByID = productsService.getProductByID(id);
        return conversionService.convert(productByID, ProductDTO.class);
    }

    @ApiOperation(value = "Create Product")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "ok"),
            @ApiResponse(code = 500, message = "An unexpected error occured")
    })
    @PostMapping(value = "/stores/{id}/products", produces = MediaType.APPLICATION_JSON_VALUE)
    public ProductDTO createProduct(
            @ApiParam(
                    value = "id",
                    name = "Store ID",
                    example = "ddf24dc6-1a2d-4391-8f34-c5c322b21c1e")
            @PathVariable String id,
            @RequestBody ProductDraftDTO productDraftDTO
    ) throws InterruptedException, ExecutionException, SbhException {
        return productsService.createProduct(productDraftDTO);
    }
}
