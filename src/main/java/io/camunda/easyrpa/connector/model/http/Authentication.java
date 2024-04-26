package io.camunda.easyrpa.connector.model.http;

import io.camunda.connector.feel.annotation.FEEL;
import io.camunda.connector.generator.java.annotation.TemplateProperty;
import io.camunda.easyrpa.connector.model.http.enums.HttpMethod;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class Authentication {

    @TemplateProperty(ignore = true)
    private String grantType;
    @TemplateProperty(ignore = true)
    private String authTokenEndpoint;
    @TemplateProperty(ignore = true)
    private HttpMethod method;
    @FEEL
    @NotEmpty
    @TemplateProperty(
            group = "authentication",
            description = "Client ID from the OAuth client")
    private String clientId;
    @FEEL
    @NotEmpty
    @TemplateProperty(
            group = "authentication",
            description = "Client secret from the OAuth client")
    private String clientSecret;
}
