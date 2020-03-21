package com.diconiumwvv.storesservice.stores.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class StoresCustomFieldsDTO {

    private List<String> neighborhood;
}
