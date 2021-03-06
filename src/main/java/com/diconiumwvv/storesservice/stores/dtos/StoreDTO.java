package com.diconiumwvv.storesservice.stores.dtos;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.sphere.sdk.models.Address;
import io.sphere.sdk.models.GeoJSON;
import io.sphere.sdk.models.LocalizedString;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

import java.util.List;

@Data
@Builder
@JsonPropertyOrder({"id", "name", "description", "address", "neighborhood", "geoLocation", "profileImageURL", "profileVideoURL", "shopOwnerImage", "contact", "shopOwnerName"})
public class StoreDTO {

    private String id;
    private LocalizedString name;
    private LocalizedString description;
    private AddressDTO address;
    private List<String> neighborhood;
    private GeoJSON geoLocation;
    private String profileImageURL;
    private String profileVideoURL;
    private String shopOwnerImage;
    private String contact;
    private String shopOwnerName;
    private String phone;
    private String email;
    private String homepage;

    @Tolerate
    public StoreDTO() {
        // Required for Jackson
    }
}
