package com.hung.sneakery.converter;

import com.hung.sneakery.dto.PropertyDTO;

import java.util.List;
import java.util.Map;

public interface PropertyConverter {

    List<PropertyDTO> convertPropertiesToDTO(Map<String, Object> properties);

    Map<String, Object> convertPropertiesToMap(List<PropertyDTO> properties);
}
