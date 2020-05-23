package com.diconiumwvv.storesservice.stores.dtos;

import io.sphere.sdk.models.GeoJSON;
import io.sphere.sdk.models.LocalizedString;
import lombok.Data;

import java.util.List;

@Data
public class StoreDraftDTO {

    private String key;
    private LocalizedString name;
    private LocalizedString description;
    private AddressDTO address;
    private GeoJSON geoLocation;

    // customs
    private List<String> neighborhood;
    private String profileVideoURL;
    private String shopCategory;
    private String shopOwnerFirstName;
    private String shopOwnerLastName;
    private String contactEmail;
    private String contactHomepage;
    private String contactPhone;
    private String contactWhatsapp;
    private String contactInstagram;
    private String contactFacebook;
    private String contactFacetime;
    private String contactSkype;
    private String contactTwitter;
    private Boolean hasDelivery;
    private Boolean hasPickup;
    private Boolean hasShipping;

    //internal contact data
    private String ownerPhone;
    private String ownerEmail;
    private Integer numberOfEmployees;
}
