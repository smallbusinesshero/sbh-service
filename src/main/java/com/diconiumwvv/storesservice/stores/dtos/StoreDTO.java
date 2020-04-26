package com.diconiumwvv.storesservice.stores.dtos;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.sphere.sdk.models.GeoJSON;
import io.sphere.sdk.models.LocalizedString;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

import java.util.List;

@Data
@Builder
@JsonPropertyOrder({
          "id",
          "name",
          "description",
          "address",
          "neighborhood",
          "geoLocation",
          "profileImageURL",
          "profileVideoURL",
          "shopOwnerImage",
          "contact",
          "shopOwnerName",
          "phone",
          "email",
          "homepage",
          "shopCategory",
          "contactWhatsapp",
          "contactInstagram",
          "contactFacebook",
          "contactFacetime",
          "contactSkype",
          "contactTwitter",
          "hasDelivery",
          "hasPickup",
          "hasShipping"
})
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
    private String shopCategory;
    private String contactWhatsapp;
    private String contactInstagram;
    private String contactFacebook;
    private String contactFacetime;
    private String contactSkype;
    private String contactTwitter;
    private Boolean hasDelivery;
    private Boolean hasPickup;
    private Boolean hasShipping;

    @Tolerate
    public StoreDTO() {
        // Required for Jackson
    }
}
