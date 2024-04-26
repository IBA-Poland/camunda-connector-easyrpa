package io.camunda.easyrpa.connector.services.http;

import com.google.api.client.http.*;
import com.google.api.client.http.json.JsonHttpContent;
import com.google.api.client.json.gson.GsonFactory;
import io.camunda.easyrpa.connector.model.http.HttpRequestBuilder;
import io.camunda.easyrpa.connector.model.http.HttpRequestTemplate;
import org.apache.commons.text.StringEscapeUtils;

import java.io.IOException;

import static org.apache.http.entity.ContentType.APPLICATION_FORM_URLENCODED;
import static org.apache.http.entity.ContentType.APPLICATION_JSON;

public class HttpRequestMapper {

    private HttpRequestMapper() {
    }

    public static HttpRequest toHttpRequest(
            final HttpRequestFactory requestFactory, final HttpRequestTemplate request) throws IOException {
        return toHttpRequest(requestFactory, request, null);
    }

    public static HttpRequest toHttpRequest(
            final HttpRequestFactory requestFactory,
            final HttpRequestTemplate request,
            final String bearerToken)
            throws IOException {
        final GenericUrl genericUrl = new GenericUrl(request.getUrl());
        final HttpHeaders headers = createHeaders(request, bearerToken);

        if (request.getQueryParameters() != null) {
            genericUrl.putAll(request.getQueryParameters());
        }

        if (request.getBody() != null && request.getBody() instanceof String) {
            String unescapeBody = StringEscapeUtils.unescapeJson((String) request.getBody());
            request.setBody(unescapeBody);
        }

        HttpContent content = null;
        if (request.getMethod().supportsBody) {
            if (APPLICATION_FORM_URLENCODED.getMimeType().equalsIgnoreCase(headers.getContentType())) {
                content = new UrlEncodedContent(request.getBody());
            } else {
                content =
                        request.getBody() != null ? new JsonHttpContent(new GsonFactory(), request.getBody()) : null;
            }
        }

        return new HttpRequestBuilder(request.getMethod(), genericUrl, headers, content, false)
                .build(requestFactory);
    }

    public static HttpHeaders createHeaders(final HttpRequestTemplate request, String bearerToken) {
        final HttpHeaders httpHeaders = new HttpHeaders();
        if (request.getMethod().supportsBody) {
            httpHeaders.setContentType(APPLICATION_JSON.getMimeType());
        }
        if (bearerToken != null && !bearerToken.isEmpty()) {
            httpHeaders.setAuthorization("Bearer " + bearerToken);
        }
        return httpHeaders;
    }
}
