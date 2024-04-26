package io.camunda.easyrpa.connector.model.http;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class HttpAuthRequest {

    private Authentication authentication;
    private HttpRequestTemplate httpRequestTemplate;
}
