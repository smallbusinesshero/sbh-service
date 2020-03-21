package com.diconiumwvv.storesservice.stores.dtos;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

import java.util.List;

@Data
@Builder
public class GeoLocationDTO {

    private String type;
    private List<CoordinateDTO> coordinates;

    @Tolerate
    public GeoLocationDTO() {
        // Required for Jackson
    }
}
