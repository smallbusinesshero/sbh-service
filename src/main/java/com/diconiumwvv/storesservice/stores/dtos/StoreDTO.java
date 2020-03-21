package com.diconiumwvv.storesservice.stores.dtos;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

import java.util.List;
import java.util.Locale;
import java.util.Map;

@Data
@Builder
public class StoreDTO {

    private String id;
    private Map<Locale, String> name;
    private Map<Locale, String> description;
    private AddressDTO address;
    private List<String> neighborhood;
    private GeoLocationDTO geoLocation;

    @Tolerate
    public StoreDTO() {
        // Required for Jackson
    }
}
