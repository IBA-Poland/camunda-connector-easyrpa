package io.camunda.easyrpa.connector.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class JsonHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(JsonHelper.class);

    public static JsonNode getAsJsonElement(final String strResponse, final ObjectMapper mapper) {
        return Optional.ofNullable(strResponse)
                .filter(response -> !response.isBlank())
                .map(
                        response -> {
                            try {
                                return mapper.readTree(response);
                            } catch (JsonProcessingException e) {
                                 LOGGER.error("Wasn't able to create a JSON node from string: " + strResponse);
                                throw new RuntimeException(e);
                            }
                        })
                .orElse(null);
    }
}
