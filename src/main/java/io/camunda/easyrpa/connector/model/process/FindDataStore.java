package io.camunda.easyrpa.connector.model.process;

import io.camunda.connector.generator.java.annotation.TemplateProperty;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public final class FindDataStore extends EasyRPAInput{
    @NotEmpty
    @TemplateProperty(group = "process", label = "name", description = "data_store name")
    private String name;
}
