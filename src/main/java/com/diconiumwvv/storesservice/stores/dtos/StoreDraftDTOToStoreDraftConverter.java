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

        Map<String, Object> customFields = new HashMap<String, Object>() {{
                    put("neighborhood", storeDraftDTO.getNeighborhood());
                    put("profileImageURL", storeDraftDTO.getProfileImageURL());
                    put("profileVideoURL", storeDraftDTO.getProfileVideoURL());
                    put("shopOwnerImage", storeDraftDTO.getShopOwnerImage());
                    put("contact", storeDraftDTO.getContact());
                    put("shopOwnerName", storeDraftDTO.getShopOwnerName());
                    put("phone", storeDraftDTO.getPhone());
                    put("email", storeDraftDTO.getEmail());
                    put("homepage", storeDraftDTO.getHomepage());
                }};
        customFields.values().removeIf(Objects::isNull);
        draftBuilder.custom(CustomFieldsDraftBuilder.ofTypeKey("channel-neighborhoods").addObjects(customFields).build());

        return draftBuilder.build();
    }
}
