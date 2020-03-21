package com.diconiumwvv.storesservice.stores.dtos;

import com.fasterxml.jackson.databind.util.Converter;
import io.sphere.sdk.channels.Channel;

import java.util.ArrayList;

public class StoreToStoreDTOConverter implements Converter<Channel, StoreDTO> {

    @Override
    public StoreDTO convert(Channel channel) {

        StoreDTO.StoreDTOBuilder builder = StoreDTO.builder();
        builder.id(channel.getId())
                .name(channel.getName())
                .description(channel.getDescription())
                .address(channel.getAddress())
                .geoLocation(channel.getGeoLocation())
                .neighborhood(new ArrayList<>(channel.getCustom().getFieldAsStringSet("neighborhood")))
                .profileImageURL(channel.getCustom().getFieldAsString("profileImageURL"))
                .shopOwnerImage(channel.getCustom().getFieldAsString("shopOwnerImage"))
                .shopOwnerName(channel.getCustom().getFieldAsString("shopOwnerName"))
                .contact(channel.getCustom().getFieldAsString("contact"));

        return builder.build();
    }
}
