package com.diconiumwvv.storesservice.stores;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GeographicCoordinateDTO {

    private String latitude;
    private String longitude;
}
