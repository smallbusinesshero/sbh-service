package com.diconiumwvv.storesservice.stores;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class GeoLocationDTO {

    private String type;
    private List<CoordinateDTO> coordinates;
}
