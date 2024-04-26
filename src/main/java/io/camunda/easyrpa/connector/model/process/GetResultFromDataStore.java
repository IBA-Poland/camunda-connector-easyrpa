package io.camunda.easyrpa.connector.model.process;

import io.camunda.connector.generator.java.annotation.TemplateProperty;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public final class GetResultFromDataStore extends EasyRPAInput {
    @NotEmpty
    @TemplateProperty(group = "process", label = "id", description = "id data store")
    private Integer idDataStore;

    @NotEmpty
    @TemplateProperty(group = "process", label = "name", description = "document name")
    private String docName;
}
