package com.diconiumwvv.storesservice.stores.dtos;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

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

    @Tolerate
    public AddressDTO() {
        // Required for Jackson
    }
}
