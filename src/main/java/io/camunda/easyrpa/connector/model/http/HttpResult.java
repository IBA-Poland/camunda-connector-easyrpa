package io.camunda.easyrpa.connector.model.http;

import lombok.Data;

import java.util.Map;

@Data
public class HttpResult {

    private int status;
    private Map<String, Object> headers;
    private Object body;
}
