package com.diconiumwvv.storesservice.stores;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StoreDTO {

     private String id;
     private String name;
     private String address;
     private GeographicCoordinateDTO geographicCoordinate;
}
