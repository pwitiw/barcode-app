package com.frontwit.barcodeapp.server.application.synchronization;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;

import static com.frontwit.barcodeapp.server.application.synchronization.Symbol.*;

class Features {
    private static final Logger LOG = LoggerFactory.getLogger(Order.class);

    private Dictionary dictionary;
    private Map<String, Long> features;


    Features(String features, Dictionary dictionary, ObjectMapper objectMapper) {
        this.dictionary = dictionary;
        try {
            this.features = objectMapper.readValue(features, new TypeReference<Map<String,Long>>(){});
        } catch (IOException e) {
            LOG.warn("Bad JSON Format: " + features);
        }
    }

    String getColor() {
        return getValueOrEmpty(COLOR);
    }

    String getCutter() {
        return getValueOrEmpty(CUTTER);
    }

    String getSize() {
        return getValueOrEmpty(SIZE);
    }

    private String getValueOrEmpty(Symbol symbol) {
        if (features != null) {
            return dictionary.getValue(features.get(symbol.getValue()));
        }
        return "";
    }

}
