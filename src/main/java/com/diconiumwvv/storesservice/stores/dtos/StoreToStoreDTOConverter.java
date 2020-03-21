package com.diconiumwvv.storesservice.stores.dtos;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.sphere.sdk.channels.Channel;
import org.springframework.core.convert.converter.Converter;

import java.util.List;
import java.util.Optional;

public class StoreToStoreDTOConverter implements Converter<Channel, StoreDTO> {

    private static final TypeReference<List<String>> LIST_TYPE_REFERENCE = new TypeReference<List<String>>() {
    };

    @Override
    public StoreDTO convert(Channel channel) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.readerFor(new TypeReference<List<String>>() {
        });
        StoreDTO.StoreDTOBuilder builder = StoreDTO.builder();
        builder.id(channel.getId())
                .name(channel.getName())
                .description(channel.getDescription())
                .address(channel.getAddress())
                .geoLocation(channel.getGeoLocation());

        Optional.ofNullable(channel.getCustom()).ifPresent(customFields -> {
            List<String> neighborhood = customFields.getField("neighborhood", LIST_TYPE_REFERENCE);
            builder.neighborhood(neighborhood);
        });

        return builder.build();
    }
}
