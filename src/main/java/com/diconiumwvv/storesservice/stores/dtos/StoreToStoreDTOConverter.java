package com.diconiumwvv.storesservice.stores.dtos;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.models.Address;
import org.springframework.core.convert.converter.Converter;

import java.util.List;
import java.util.Optional;

public class StoreToStoreDTOConverter implements Converter<Channel, StoreDTO> {

    private static final TypeReference<List<String>> LIST_TYPE_REFERENCE = new TypeReference<List<String>>() {
    };
    public static final String NEIGHBORHOOD = "neighborhood";
    public static final String SHOP_OWNER_IMAGE = "shopOwnerImage";
    public static final String CONTACT = "contact";
    public static final String PROFILE_VIDEO_URL = "profileVideoURL";
    public static final String PROFILE_IMAGE_URL = "profileImageURL";
    public static final String SHOP_OWNER_FIRSTNAME = "shopOwnerFirstName";
    public static final String SHOP_OWNER_LASTNAME = "shopOwnerLastName";
    public static final String CONTACT_EMAIL = "contactEmail";
    public static final String CONTACT_HOMEPAGE = "contactHomepage";
    public static final String CONTACT_PHONE = "contactPhone";
    public static final String SHOP_CATEGORY = "shopCategory";
    public static final String CONTACT_WHATSAPP = "contactWhatsapp";
    public static final String CONTACT_INSTAGRAM = "contactInstagram";
    public static final String CONTACT_FACEBOOK = "contactFacebook";
    public static final String CONTACT_FACETIME = "contactFacetime";
    public static final String CONTACT_SKYPE = "contactSkype";
    public static final String CONTACT_TWITTER = "contactTwitter";
    public static final String HAS_DELIVERY = "hasDelivery";
    public static final String HAS_PICKUP = "hasPickup";
    public static final String HAS_SHIPPING = "hasShipping";

    private AddressDTO convertAddress(Address address) {
        if (address == null) {
            return new AddressDTO();
        }
        return AddressDTO.builder()
                .streetName(address.getStreetName())
                .streetNumber(address.getStreetNumber())
                .postalCode(address.getPostalCode())
                .city(address.getCity())
                .region(address.getRegion())
                .state(address.getState())
                .country(address.getCountry().getAlpha2())
                .build();
    }

    @Override
    public StoreDTO convert(Channel channel) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.readerFor(new TypeReference<List<String>>() {
        });
        StoreDTO.StoreDTOBuilder builder = StoreDTO.builder();
        builder.id(channel.getId())
                .name(channel.getName())
                .description(channel.getDescription())
                .address(convertAddress(channel.getAddress()))
                .geoLocation(channel.getGeoLocation());

        Optional.ofNullable(channel.getCustom()).ifPresent(customFields -> {
            builder.neighborhood(customFields.getField(NEIGHBORHOOD, LIST_TYPE_REFERENCE));
            builder.shopOwnerFirstName(customFields.getFieldAsString(SHOP_OWNER_FIRSTNAME));
            builder.shopOwnerLastName(customFields.getFieldAsString(SHOP_OWNER_LASTNAME));
            builder.shopOwnerImage(customFields.getFieldAsString(SHOP_OWNER_IMAGE));
            builder.contact(customFields.getFieldAsString(CONTACT));
            builder.profileVideoURL(customFields.getFieldAsString(PROFILE_VIDEO_URL));
            builder.profileImageURL(customFields.getFieldAsString(PROFILE_IMAGE_URL));
            builder.shopCategory(customFields.getFieldAsString(SHOP_CATEGORY));
            builder.contactEmail(customFields.getFieldAsString(CONTACT_EMAIL));
            builder.contactHomepage(customFields.getFieldAsString(CONTACT_HOMEPAGE));
            builder.contactPhone(customFields.getFieldAsString(CONTACT_PHONE));
            builder.contactWhatsapp(customFields.getFieldAsString(CONTACT_WHATSAPP));
            builder.contactInstagram(customFields.getFieldAsString(CONTACT_INSTAGRAM));
            builder.contactFacebook(customFields.getFieldAsString(CONTACT_FACEBOOK));
            builder.contactFacetime(customFields.getFieldAsString(CONTACT_FACETIME));
            builder.contactSkype(customFields.getFieldAsString(CONTACT_SKYPE));
            builder.contactTwitter(customFields.getFieldAsString(CONTACT_TWITTER));
            builder.hasDelivery(customFields.getFieldAsBoolean(HAS_DELIVERY));
            builder.hasPickup(customFields.getFieldAsBoolean(HAS_PICKUP));
            builder.hasShipping(customFields.getFieldAsBoolean(HAS_SHIPPING));
        });

        return builder.build();
    }
}
