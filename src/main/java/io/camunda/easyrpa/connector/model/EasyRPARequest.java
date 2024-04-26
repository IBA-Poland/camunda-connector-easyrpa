package io.camunda.easyrpa.connector.model;

import io.camunda.connector.generator.java.annotation.TemplateProperty;
import io.camunda.easyrpa.connector.model.process.EasyRPAInput;
import io.camunda.easyrpa.connector.model.http.Authentication;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class EasyRPARequest {

    @NotNull
    private Authentication authentication;
    @NotEmpty
    @TemplateProperty(group = "commonUrl", type = TemplateProperty.PropertyType.Text)
    private String commonUrl;
    @NotNull
    private EasyRPAInput process;
}
