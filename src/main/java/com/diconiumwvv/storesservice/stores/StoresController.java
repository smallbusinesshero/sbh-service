package com.diconiumwvv.storesservice.stores;

import com.diconiumwvv.storesservice.stores.dtos.StoreDTO;
import com.diconiumwvv.storesservice.stores.dtos.StoreSearchDTO;
import io.sphere.sdk.channels.Channel;
import io.swagger.annotations.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.ExecutionException;

@CrossOrigin(origins = {
        "http://localhost:3000",
        "https://dds-wvv-frontend.herokuapp.com",
        "https://dev-dds-wvv-frontend.herokuapp.com",
        "http://smallbusinesshero.de",
        "http://www.smallbusinesshero.de",
        "https://smallbusinesshero.netlify.app"
})
@RestController
@Api(tags = "Stores API")
public class StoresController {

    @Resource
    private ConversionService conversionService;

    @Resource
    private StoresService storesService;

    @ApiOperation(value = "get stores / or filter by query")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "ok"),
            @ApiResponse(code = 500, message = "An unexpected error occurred")
    })
    @GetMapping(value = "/stores/", produces = "application/json")
    public List<StoreDTO> getStoresByQuery(
            @ApiParam(value = "id", example = "Neukölln")
            @RequestParam(required = false)
                    String neighborhood
    ) throws ExecutionException, InterruptedException {
        try {
            return storesService.searchStore(neighborhood);
        } catch (Exception e) {
            return storesService.getAllStores();
        }
    }

    @ApiOperation(value = "get a specific store by ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "ok"),
            @ApiResponse(code = 500, message = "An unexpected error occurred")
    })
    @GetMapping(value = "/stores/{id}", produces = "application/json")
    public StoreDTO getStoreById(@ApiParam(value = "id", example = "ddf24dc6-1a2d-4391-8f34-c5c322b21c1e") @PathVariable String id) throws ExecutionException, InterruptedException {
        Channel storeForID = storesService.getStoreForID(id);
        return conversionService.convert(storeForID, StoreDTO.class);
    }
}
