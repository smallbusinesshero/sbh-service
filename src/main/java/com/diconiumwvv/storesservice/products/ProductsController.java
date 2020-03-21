package com.diconiumwvv.storesservice.products;

import com.diconiumwvv.storesservice.products.dtos.*;
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
import java.util.Locale;

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
        return ProductDTO.builder()
                .id("ddf24dc6-1a2d-4391-8f34-c5c322b21c1e")
                .categories(Collections.singletonList("Garten"))
                .name(Collections.singletonMap(Locale.GERMANY, "Silikontopf"))
                .description(Collections.singletonMap(Locale.GERMANY, "Silikonübertopf in verschiedemen Farbenen."))
                .slug(Collections.singletonMap(Locale.GERMANY, "silikontopf"))
                .metaTitle(Collections.singletonMap(Locale.GERMANY, ""))
                .metaDescription(Collections.singletonMap(Locale.GERMANY, ""))
                .masterVariant(ProductVariantDTO.builder()
                        .id("1")
                        .sku("golden-silkontopf")
                        .prices(Collections.singletonList(PriceDTO.builder().build()))
                        .images(Collections.singletonList(ImageDTO.builder().build()))
                        .attributes(Collections.singletonList(AttributesDTO.builder().build()))
                        .assets(Collections.singletonList(AssetsDTO.builder().build()))
                        .build())
                .build();
    }
}
