package com.hung.sneakery.converter.impl;

import com.hung.sneakery.converter.PropertyConverter;
import com.hung.sneakery.dto.PropertyDTO;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
@SuppressWarnings("unchecked")
public class PropertyConverterImpl implements PropertyConverter {

    public List<PropertyDTO> convertPropertiesToDTO(Map<String, Object> properties) {
        if (properties == null || properties.isEmpty()) {
            return Collections.emptyList();
        }

        return properties.entrySet().stream()
                .map(entry -> {
                    PropertyDTO propertyDTO = new PropertyDTO();
                    propertyDTO.setName(entry.getKey());
                    propertyDTO.setType(entry.getValue().toString());

                    // Check if the value is a map, indicating options are present
                    if (entry.getValue() instanceof Map) {
                        Map<String, Object> optionsMap = (Map<String, Object>) entry.getValue();
                        List<String> options = new ArrayList<>(optionsMap.keySet());
                        propertyDTO.setOptions(options);
                    }
                    return propertyDTO;
                })
                .collect(Collectors.toList());
    }


    public Map<String, Object> convertPropertiesToMap(List<PropertyDTO> properties) {
        if (properties == null || properties.isEmpty()) {
            return new HashMap<>();
        }

        return properties.stream()
                .collect(Collectors.toMap(
                        PropertyDTO::getName,
                        propertyDTO -> {
                            if (Objects.nonNull(propertyDTO.getOptions()) && !propertyDTO.getOptions().isEmpty()) {
                                // If options exist, return a map containing 'type' and 'options'
                                Map<String, Object> propertyMap = new HashMap<>();
                                propertyMap.put("type", propertyDTO.getType());
                                propertyMap.put("options", propertyDTO.getOptions());
                                return propertyMap;
                            } else {
                                // If no options, return just the type
                                return propertyDTO.getType();
                            }
                        }));
    }
}
