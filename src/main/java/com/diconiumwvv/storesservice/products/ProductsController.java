package com.diconiumwvv.storesservice.products;

import com.diconiumwvv.storesservice.products.dtos.ProductDTO;
import io.sphere.sdk.products.Product;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@CrossOrigin(origins = {
        "http://localhost:3000",
        "https://dds-wvv-frontend.herokuapp.com"
})
@RestController
@Api(tags = "Products API")
public class ProductsController {

    @Resource
    ConversionService conversionService;

    @Resource
    ProductsService productsService;

    @ApiOperation(value = "get all products for one specific store")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "ok"),
            @ApiResponse(code = 500, message = "An unexpected error occurred")
    })
    @GetMapping(value = "/products/")
    public List<ProductDTO> getProductsByStoreId(@RequestParam String storeId) throws ExecutionException, InterruptedException {
        List<Product> productsByStore = productsService.getProductsByStore(storeId);
        return productsByStore.stream()
                .map(product -> conversionService.convert(product, ProductDTO.class))
                .collect(Collectors.toList());
    }

    @ApiOperation(value = "get one specific product by ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "ok"),
            @ApiResponse(code = 500, message = "An unexpected error occurred")
    })
    @GetMapping(value = "/products/{id}")
    public ProductDTO getProductById(@PathVariable String id) throws ExecutionException, InterruptedException {
        Product productByID = productsService.getProductByID(id);
        return conversionService.convert(productByID, ProductDTO.class);
    }

    /**
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
     **/
}
