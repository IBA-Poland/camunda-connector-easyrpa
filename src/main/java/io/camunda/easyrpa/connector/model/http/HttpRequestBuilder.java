package io.camunda.easyrpa.connector.model.http;

import com.google.api.client.http.*;
import io.camunda.easyrpa.connector.model.http.enums.HttpMethod;
import lombok.AllArgsConstructor;
import lombok.Setter;

import java.io.IOException;

@Setter
@AllArgsConstructor
public final class HttpRequestBuilder {

    private HttpMethod method;
    private GenericUrl genericUrl;
    private HttpHeaders headers;
    private HttpContent content;
    private boolean followRedirects;

    public HttpRequest build(final HttpRequestFactory requestFactory) throws IOException {
        final var httpRequest = requestFactory.buildRequest(method.name(), genericUrl, content);
        httpRequest.setFollowRedirects(this.followRedirects);
        if (headers != null) {
            httpRequest.setHeaders(headers);
        }
        return httpRequest;
    }
}
