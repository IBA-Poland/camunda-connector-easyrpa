package io.camunda.easyrpa.connector.model.http.enums;

public enum HttpMethod {

    POST(true),
    GET(false),
    DELETE(false),
    PATCH(true),
    PUT(true);

    public final boolean supportsBody;

    HttpMethod(boolean supportsBody) {
        this.supportsBody = supportsBody;
    }
}
