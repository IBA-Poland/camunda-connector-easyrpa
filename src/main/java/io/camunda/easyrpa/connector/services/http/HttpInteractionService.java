package io.camunda.easyrpa.connector.services.http;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpResponseException;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
    import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import io.camunda.connector.api.error.ConnectorException;
import io.camunda.easyrpa.connector.model.http.error.ErrorResponse;
import io.camunda.easyrpa.connector.model.http.HttpResult;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpInteractionService {

    private static final Logger LOGGER = LoggerFactory.getLogger(HttpInteractionService.class);

    private final ObjectMapper objectMapper;

    public HttpInteractionService(final ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public HttpResponse executeHttpRequest(
            com.google.api.client.http.HttpRequest externalRequest)
            throws IOException {
        HttpResponse response = null;
        try {
            response = externalRequest.execute();
            return response;
        } catch (HttpResponseException hrex) {
            var errorCode = String.valueOf(hrex.getStatusCode());
            var errorMessage = hrex.getMessage();
            if (hrex.getContent() != null) {
                try {
                    final var errorContent = objectMapper.readValue(hrex.getContent(), ErrorResponse.class);
                    errorCode = errorContent.getErrorCode();
                    errorMessage = errorContent.getError();
                } catch (Exception e) {
                    LOGGER.warn("Error response cannot be parsed as JSON! Will use the plain message.");
                }
            }
            throw new ConnectorException(errorCode, errorMessage, hrex);
        }
    }

    public <T extends HttpResult> T toHttpResponse(
            final HttpResponse externalResponse, final Class<T> resultClass)
            throws InstantiationException, IllegalAccessException {
        T connectorResult = resultClass.newInstance();
        connectorResult.setStatus(externalResponse.getStatusCode());
        final Map<String, Object> headers = new HashMap<>();
        externalResponse
                .getHeaders()
                .forEach(
                        (k, v) -> {
                            if (v instanceof List && ((List<?>) v).size() == 1) {
                                headers.put(k, ((List<?>) v).get(0));
                            } else {
                                headers.put(k, v);
                            }
                        });
        connectorResult.setHeaders(headers);
        try (InputStream content = externalResponse.getContent()) {
            String bodyString = null;
            if (content != null) {
                bodyString = new String(content.readAllBytes(), StandardCharsets.UTF_8);
            }

            if (bodyString != null) {
                if (isJSONValid(bodyString)) {
                    Object body = objectMapper.readValue(bodyString, Object.class);
                    connectorResult.setBody(body);
                } else {
                    connectorResult.setBody(bodyString);
                }
            }
        } catch (final Exception e) {
            LOGGER.error("Failed to parse external response: {}", externalResponse, e);
        }
        return connectorResult;
    }

    protected static boolean isJSONValid(String jsonInString) {
        try (JsonReader reader = new JsonReader(new StringReader(jsonInString))) {
            final JsonElement jsonElement = JsonParser.parseReader(reader);
            return jsonElement.isJsonObject() || jsonElement.isJsonArray();
        } catch (JsonParseException | IOException e) {
            return false;
        }
    }
}
