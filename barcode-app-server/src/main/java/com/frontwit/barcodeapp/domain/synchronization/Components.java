package com.frontwit.barcodeapp.domain.synchronization;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.frontwit.barcodeapp.domain.order.dto.ComponentDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

class Components {
    private static final Logger LOG = LoggerFactory.getLogger(Components.class);

    private Component[] components;

    Components(String jsonArray, ObjectMapper objectMapper) {
        try {
            this.components = objectMapper.readValue(jsonArray, Component[].class);
        } catch (IOException e) {
            LOG.debug("Order from Frontwit DB: " + jsonArray);
        }
    }

    List<ComponentDto> composeComponents() {
        if (components == null) {
            return Collections.emptyList();
        }
        return Arrays.stream(components)
                .map(Component::dto)
                .collect(Collectors.toList());
    }

    // todo co to jest element
    private class Component {
        @JsonProperty("nr")
        private long id;
        @JsonProperty("l")
        private int length;
        @JsonProperty("w")
        private int width;
        @JsonProperty(value = "q")
        private int quantity;
        @JsonProperty(value = "el")
        private String element;
        @JsonProperty(value = "com")
        private String comment;

        ComponentDto dto() {
            return ComponentDto.builder()
                    .id(id)
                    .height(length)
                    .width(width)
                    .comment(comment)
                    .quantity(quantity)
                    .build();
        }
    }
}
