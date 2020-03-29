package com.diconiumwvv.storesservice.stores.dtos;

import io.sphere.sdk.channels.ChannelDraft;
import io.sphere.sdk.channels.ChannelDraftBuilder;
import io.sphere.sdk.types.CustomFieldsDraftBuilder;
import org.springframework.core.convert.converter.Converter;

import java.util.HashMap;
import java.util.Optional;

public class StoreDraftDTOToStoreDraftConverter implements Converter<StoreDraftDTO, ChannelDraft> {

    @Override
    public ChannelDraft convert(StoreDraftDTO storeDraftDTO) {
        ChannelDraftBuilder draftBuilder = ChannelDraftBuilder.of(storeDraftDTO.getKey());

        Optional.ofNullable(storeDraftDTO.getName()).ifPresent(draftBuilder::name);
        Optional.ofNullable(storeDraftDTO.getDescription()).ifPresent(draftBuilder::description);
        Optional.ofNullable(storeDraftDTO.getAddress()).ifPresent(draftBuilder::address);
        Optional.ofNullable(storeDraftDTO.getGeoLocation()).ifPresent(draftBuilder::geoLocation);

        draftBuilder.custom(
                CustomFieldsDraftBuilder.ofTypeKey("channel-neighborhoods")
                        .addObjects(new HashMap<String, Object>() {{
                            Optional.ofNullable(storeDraftDTO.getNeighborhood()).ifPresent(o -> put("neighborhood", o));
                            Optional.ofNullable(storeDraftDTO.getProfileImageURL()).ifPresent(o -> put("profileImageURL", o));
                            Optional.ofNullable(storeDraftDTO.getProfileVideoURL()).ifPresent(o -> put("profileVideoURL", o));
                            Optional.ofNullable(storeDraftDTO.getShopOwnerImage()).ifPresent(o -> put("shopOwnerImage", o));
                            Optional.ofNullable(storeDraftDTO.getContact()).ifPresent(o -> put("contact", o));
                            Optional.ofNullable(storeDraftDTO.getShopOwnerName()).ifPresent(o -> put("shopOwnerName", o));
                            Optional.ofNullable(storeDraftDTO.getPhone()).ifPresent(o -> put("phone", o));
                            Optional.ofNullable(storeDraftDTO.getEmail()).ifPresent(o -> put("email", o));
                            Optional.ofNullable(storeDraftDTO.getHomepage()).ifPresent(o -> put("homepage", o));
                        }}).build()
        );

        return draftBuilder.build();
    }
}
