package com.diconiumwvv.storesservice.products;

import com.diconiumwvv.storesservice.products.dtos.ProductDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@CrossOrigin(origins = {
        "http://localhost:3000",
        "https://dds-wvv-frontend.herokuapp.com"
})
@RestController
@Api(tags = "Products API")
public class ProductsController {

    @ApiOperation(value = "get all products for one specific store")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "ok"),
            @ApiResponse(code = 500, message = "An unexpected error occurred")
    })
    @GetMapping(value = "/products/")
    public List<ProductDTO> getProductsByStoreId(@RequestParam String storeId) {
        return Collections.singletonList(getMockProduct());
    }

    @ApiOperation(value = "get all products for one specific store")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "ok"),
            @ApiResponse(code = 500, message = "An unexpected error occurred")
    })
    @GetMapping(value = "/products/{id}")
    public ProductDTO getProductById(@RequestParam String id) {
        return getMockProduct();
    }

    private ProductDTO getMockProduct() {
        return ProductDTO.builder().build();
    }
}
