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

    //internal contact data
    private String ownerPhone;
    private String ownerEmail;
    private Integer numberOfEmployees;
}
