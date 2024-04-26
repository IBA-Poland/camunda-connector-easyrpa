package io.camunda.easyrpa.connector.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.*;
import io.camunda.easyrpa.connector.constants.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class ResponseConverter {

    private final Gson gson;
    private final ObjectMapper objectMapper;
    private boolean nestedElement;
    private JsonElement jsonElement;

    private static final Logger LOGGER = LoggerFactory.getLogger(ResponseConverter.class);

    public ResponseConverter(Gson gson, ObjectMapper objectMapper) {
        this.gson = gson;
        this.objectMapper = objectMapper;
    }

    public final void extractResponseParams(Object body, String keyString, JsonObject respParam) {
        List<String> keys = List.of(keyString.split(Constants.VARIABLE_PATH_SEPARATOR));
        nestedElement = false;
        keys.forEach(key -> extractValueByKey(body,key));
        respParam.add(keys.getLast(),jsonElement);
    }
    public final void extractArrayResponseParams(Object body, String keyString, JsonArray array) {
        JsonObject respParam = new JsonObject();
        extractResponseParams(body,keyString,respParam);
        array.add(gson.toJson(respParam));
    }

    public final void extractValueByKey(Object body, String key){
        String stringBody = nestedElement ? gson.toJson(jsonElement) : gson.toJson(body) ;
        JsonObject jsonBody = gson.fromJson(stringBody, JsonObject.class);
        jsonElement = jsonBody.get(key);
        nestedElement = true;
    }

    public final Object convertToResponse(JsonElement jsonResp){
        return convertStringToResponse(gson.toJson(jsonResp));
    }
    public final Object convertToResponse(JsonObject jsonResp){
        return convertStringToResponse(gson.toJson(jsonResp));
    }

    public final Object convertStringToResponse(String respString){
        Object respObject = null;
        try {
            respObject = objectMapper.readValue(respString, Object.class);
        } catch (JsonProcessingException e) {
            LOGGER.error("The response could not be converted: {}", respString, e);
        }
        return respObject;
    }
}
