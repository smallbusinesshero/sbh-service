package com.diconiumwvv.storesservice.stores;

import lombok.Builder;
import lombok.Data;

import java.util.Locale;
import java.util.Map;

@Data
@Builder
public class StoreDTO {

     private String id;
     private Map<Locale, String> name;
     private Map<Locale, String> description;
     private AddressDTO address;
     private String neighborhood;
     private GeoLocationDTO geoLocation;
}
