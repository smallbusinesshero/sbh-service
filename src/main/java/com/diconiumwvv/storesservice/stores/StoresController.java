package com.diconiumwvv.storesservice.stores;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;


@RestController

@Api(tags = "Stores API")
public class StoresController {

    @ApiOperation(value = "search for stores nearby")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "ok"),
            @ApiResponse(code = 500, message = "An unexpected error occurred")
    })
    @GetMapping(value = "/v1/stores/")
    public List<StoreDTO> getStores(@RequestParam(required = false) String geographicCoordinate) {
        return Collections.singletonList(StoreDTO.builder()
                .address("Sample Address")
                .geographicCoordinate(
                        GeographicCoordinateDTO.builder().latitude("52.520008").longitude("13.404954").build())
                .id("d290f1ee-6c54-4b01-90e6-d701748f0851")
                .name("Groocery Store")
                .build());
    }
}
