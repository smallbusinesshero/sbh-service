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
    public static final String NEIGHBORHOOD = "neighborhood";
    public static final String SHOP_OWNER_NAME = "shopOwnerName";
    public static final String SHOP_OWNER_IMAGE = "shopOwnerImage";
    public static final String CONTACT = "contact";
    public static final String PROFILE_VIDEO_URL = "profileVideoURL";
    public static final String PROFILE_IMAGE_URL = "profileImageURL";
    public static final String PHONE = "phone";
    public static final String EMAIL = "email";
    public static final String HOMEPAGE = "homepage";

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
            List<String> neighborhood = customFields.getField(NEIGHBORHOOD, LIST_TYPE_REFERENCE);
            builder.neighborhood(neighborhood);
            String shopOwnerName = customFields.getFieldAsString(SHOP_OWNER_NAME);
            builder.shopOwnerName(shopOwnerName);
            String shopOwnerImage = customFields.getFieldAsString(SHOP_OWNER_IMAGE);
            builder.shopOwnerImage(shopOwnerImage);
            String contact = customFields.getFieldAsString(CONTACT);
            builder.contact(contact);
            String profileVideoURL = customFields.getFieldAsString(PROFILE_VIDEO_URL);
            builder.profileVideoURL(profileVideoURL);
            String profileImageURL = customFields.getFieldAsString(PROFILE_IMAGE_URL);
            builder.profileImageURL(profileImageURL);
            String phone = customFields.getFieldAsString(PHONE);
            builder.phone(phone);
            String email = customFields.getFieldAsString(EMAIL);
            builder.email(email);
            String homepage = customFields.getFieldAsString(HOMEPAGE);
            builder.homepage(homepage);
        });

        return builder.build();
    }
}
