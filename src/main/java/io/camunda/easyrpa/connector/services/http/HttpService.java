package io.camunda.easyrpa.connector.services.http;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpResponse;
import io.camunda.easyrpa.connector.constants.Constants;
import io.camunda.easyrpa.connector.converter.RequestConverter;
import io.camunda.easyrpa.connector.model.http.HttpAuthRequest;
import io.camunda.easyrpa.connector.model.http.HttpRequestTemplate;
import io.camunda.easyrpa.connector.model.http.HttpResult;
import io.camunda.easyrpa.connector.utils.JsonHelper;

import java.io.IOException;
import java.util.Optional;


public class HttpService {

    private final ObjectMapper objectMapper;
    private final HttpRequestFactory requestFactory;

    public HttpService(final ObjectMapper objectMapper, final HttpRequestFactory requestFactory) {
        this.objectMapper = objectMapper;
        this.requestFactory = requestFactory;
    }

    public HttpResult executeConnectorRequest(final HttpAuthRequest request)
            throws IOException, InstantiationException, IllegalAccessException {
        return executeRequestDirectly(request);
    }

    private HttpResult executeRequestDirectly(HttpAuthRequest request)
            throws IOException, InstantiationException, IllegalAccessException {
        HttpInteractionService httpInteractionService = new HttpInteractionService(objectMapper);
        String bearerToken = getOAuthAccessToken(request);
        HttpRequestTemplate reqTemplate = request.getHttpRequestTemplate();
        HttpRequest httpRequest = HttpRequestMapper.toHttpRequest(requestFactory, reqTemplate, bearerToken);
        HttpResponse httpResponse = httpInteractionService.executeHttpRequest(httpRequest);
        return httpInteractionService.toHttpResponse(httpResponse, HttpResult.class);
    }

    private String getOAuthAccessToken(HttpAuthRequest request) throws IOException {
        HttpInteractionService interactionService = new HttpInteractionService(objectMapper);
        HttpRequestTemplate reqTemplate = RequestConverter.toHttpRequestTemplate(request);
        HttpRequest httpRequest = HttpRequestMapper.toHttpRequest(requestFactory, reqTemplate);
        HttpResponse httpResponse = interactionService.executeHttpRequest(httpRequest);
        return this.extractOAuthAccessToken(httpResponse);
    }

    public String extractOAuthAccessToken(HttpResponse response) throws IOException {
        return Optional.ofNullable(
                        JsonHelper.getAsJsonElement(response.parseAsString(), objectMapper))
                .map(jsonNode -> jsonNode.findValue(Constants.ACCESS_TOKEN).asText())
                .orElse(null);
    }
}
