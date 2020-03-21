package com.diconiumwvv.storesservice.stores.dtos;

import io.sphere.sdk.models.LocalizedString;
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
    private LocalizedString name;
    private LocalizedString description;
    private AddressDTO address;
    private List<String> neighborhood;
    private GeoLocationDTO geoLocation;
    private String profileImageURL;
    private String shopOwnerImage;
    private String contact;
    private String shopOwnerName;

    @Tolerate
    public StoreDTO() {
        // Required for Jackson
    }
}
