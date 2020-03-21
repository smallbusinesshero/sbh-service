package com.diconiumwvv.storesservice.products;

import com.diconiumwvv.storesservice.products.dtos.ProductDTO;
import io.sphere.sdk.products.ProductProjection;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.core.convert.ConversionService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = {
        "http://localhost:3000",
        "https://dds-wvv-frontend.herokuapp.com"
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
}
