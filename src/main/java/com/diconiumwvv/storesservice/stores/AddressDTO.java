package com.diconiumwvv.storesservice.stores;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddressDTO {
    private String streetName;
    private String streetNumber;
    private String postalCode;
    private String city;
    private String region;
    private String state;
    private String country;
}
