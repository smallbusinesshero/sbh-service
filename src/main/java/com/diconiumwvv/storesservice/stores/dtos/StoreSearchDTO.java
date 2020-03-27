package com.diconiumwvv.storesservice.stores.dtos;

import lombok.Data;

@Data
public class StoreSearchDTO {

    private String address;
    /**
     * Search radius in metres
     */
    private double radius;
}
