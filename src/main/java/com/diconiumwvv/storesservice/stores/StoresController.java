package com.diconiumwvv.storesservice.stores;

import com.diconiumwvv.storesservice.stores.dtos.StoreDTO;
import io.sphere.sdk.channels.Channel;
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
import java.util.concurrent.ExecutionException;

@CrossOrigin(origins = {
        "http://localhost:3000",
        "https://dds-wvv-frontend.herokuapp.com"
})
@RestController
@Api(tags = "Stores API")
public class StoresController {

    @Resource
    private ConversionService conversionService;

    @Resource
    private StoresService storesService;

    @ApiOperation(value = "search for stores in your neighborhood")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "ok"),
            @ApiResponse(code = 500, message = "An unexpected error occurred")
    })
    @GetMapping(value = "/stores/", produces = "application/json")
    public List<StoreDTO> getStoresByQuery(@ApiParam(value = "id", example = "Neukölln") @RequestParam String neighborhood) throws ExecutionException, InterruptedException {
        return storesService.getStoresByNeighborhood(neighborhood);
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
