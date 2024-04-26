package io.camunda.easyrpa.connector.model.http;

import io.camunda.easyrpa.connector.model.http.enums.HttpMethod;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HttpRequestTemplate {

    private String url;
    private HttpMethod method;
    private Object body;
    private Map<String, String> queryParameters;
}
