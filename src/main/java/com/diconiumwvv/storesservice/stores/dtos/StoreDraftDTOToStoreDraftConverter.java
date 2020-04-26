package com.diconiumwvv.storesservice.stores.dtos;

import io.sphere.sdk.channels.ChannelDraft;
import io.sphere.sdk.channels.ChannelDraftBuilder;
import io.sphere.sdk.types.CustomFieldsDraftBuilder;
import org.springframework.core.convert.converter.Converter;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class StoreDraftDTOToStoreDraftConverter implements Converter<StoreDraftDTO, ChannelDraft> {

    @Override
    public ChannelDraft convert(StoreDraftDTO storeDraftDTO) {
        ChannelDraftBuilder draftBuilder = ChannelDraftBuilder.of(storeDraftDTO.getKey());

        Optional.ofNullable(storeDraftDTO.getName()).ifPresent(draftBuilder::name);
        Optional.ofNullable(storeDraftDTO.getDescription()).ifPresent(draftBuilder::description);
        Optional.ofNullable(storeDraftDTO.getAddress()).ifPresent(draftBuilder::address);
        Optional.ofNullable(storeDraftDTO.getGeoLocation()).ifPresent(draftBuilder::geoLocation);

        Map<String, Object> customFields = new HashMap<>();
        customFields.put("neighborhood", storeDraftDTO.getNeighborhood());
        customFields.put("profileImageURL", storeDraftDTO.getProfileImageURL());
        customFields.put("profileVideoURL", storeDraftDTO.getProfileVideoURL());
        customFields.put("shopOwnerImage", storeDraftDTO.getShopOwnerImage());
        customFields.put("contact", storeDraftDTO.getContact());
        customFields.put("shopOwnerName", storeDraftDTO.getShopOwnerName());
        customFields.put("phone", storeDraftDTO.getPhone());
        customFields.put("email", storeDraftDTO.getEmail());
        customFields.put("homepage", storeDraftDTO.getHomepage());
        customFields.put("shopCategory", storeDraftDTO.getShopCategory());
        customFields.put("contactWhatsapp", storeDraftDTO.getContactWhatsapp());
        customFields.put("contactInstagram", storeDraftDTO.getContactInstagram());
        customFields.put("contactFacebook", storeDraftDTO.getContactFacebook());
        customFields.put("contactFacetime", storeDraftDTO.getContactFacetime());
        customFields.put("contactSkype", storeDraftDTO.getContactSkype());
        customFields.put("contactTwitter", storeDraftDTO.getContactTwitter());
        customFields.put("hasDelivery", storeDraftDTO.getHasDelivery());
        customFields.put("hasPickup", storeDraftDTO.getHasPickup());
        customFields.put("hasShipping", storeDraftDTO.getHasShipping());
        customFields.put("ownerPhone", storeDraftDTO.getOwnerPhone());
        customFields.put("ownerEmail", storeDraftDTO.getOwnerEmail());

        customFields.values().removeIf(Objects::isNull);
        draftBuilder.custom(CustomFieldsDraftBuilder.ofTypeKey("channel-neighborhoods").addObjects(customFields).build());

        return draftBuilder.build();
    }
}
