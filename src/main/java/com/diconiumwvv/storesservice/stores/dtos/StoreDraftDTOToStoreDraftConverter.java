package com.diconiumwvv.storesservice.stores.dtos;

import com.neovisionaries.i18n.CountryCode;
import io.sphere.sdk.channels.ChannelDraft;
import io.sphere.sdk.channels.ChannelDraftBuilder;
import io.sphere.sdk.models.AddressBuilder;
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
        Optional.ofNullable(storeDraftDTO.getGeoLocation()).ifPresent(draftBuilder::geoLocation);

        Optional.ofNullable(storeDraftDTO.getAddress()).ifPresent(addressDTO -> draftBuilder.address(
                AddressBuilder.of(CountryCode.DE)
                        .streetName(addressDTO.getStreetName())
                        .streetNumber(addressDTO.getStreetNumber())
                        .postalCode(addressDTO.getPostalCode())
                        .city(addressDTO.getCity())
                        .region(addressDTO.getRegion())
                        .state(addressDTO.getState())
                        .build()
        ));

        Map<String, Object> customFields = new HashMap<>();
        customFields.put("neighborhood", storeDraftDTO.getNeighborhood());
        customFields.put("profileVideoURL", storeDraftDTO.getProfileVideoURL());
        customFields.put("shopOwnerFirstName", storeDraftDTO.getShopOwnerFirstName());
        customFields.put("shopOwnerLastName", storeDraftDTO.getShopOwnerLastName());
        customFields.put("contactEmail", storeDraftDTO.getContactEmail());
        customFields.put("contactHomepage", storeDraftDTO.getContactHomepage());
        customFields.put("contactPhone", storeDraftDTO.getContactPhone());
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
        customFields.put("numberOfEmployees", storeDraftDTO.getNumberOfEmployees());

        customFields.values().removeIf(Objects::isNull);
        draftBuilder.custom(CustomFieldsDraftBuilder.ofTypeKey("channel-neighborhoods").addObjects(customFields).build());

        return draftBuilder.build();
    }
}
