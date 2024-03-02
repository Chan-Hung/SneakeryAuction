package com.hung.sneakery.converter.impl;

import com.hung.sneakery.converter.PropertyConverter;
import com.hung.sneakery.dto.PropertyDTO;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class PropertyConverterImpl implements PropertyConverter {

    public List<PropertyDTO> convertPropertiesToDTO(Map<String, Object> properties) {
        if (properties == null || properties.isEmpty()) {
            return Collections.emptyList();
        }

        return properties.entrySet().stream()
                .map(entry -> PropertyDTO.builder()
                        .name(entry.getKey())
                        .type(entry.getValue().toString())
                        .build())
                .collect(Collectors.toList());
    }


    public Map<String, Object> convertPropertiesToMap(List<PropertyDTO> properties) {
        if (properties == null || properties.isEmpty()) {
            return new HashMap<>();
        }
        return properties.stream()
                .collect(Collectors.toMap(PropertyDTO::getName, PropertyDTO::getType));
    }
}
