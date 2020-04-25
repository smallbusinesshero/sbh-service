package com.diconiumwvv.storesservice.stores.dtos;

import io.sphere.sdk.models.Address;
import io.sphere.sdk.models.GeoJSON;
import io.sphere.sdk.models.LocalizedString;
import lombok.Data;

import java.util.List;

@Data
public class StoreDraftDTO {

    private String key;
    private LocalizedString name;
    private LocalizedString description;
    private Address address;
    private GeoJSON geoLocation;

    // customs
    private List<String> neighborhood;
    private String profileImageURL;
    private String profileVideoURL;
    private String shopOwnerImage;
    private String contact;
    private String shopOwnerName;
    private String phone;
    private String email;
    private String homepage;
}
