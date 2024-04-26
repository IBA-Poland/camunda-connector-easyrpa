package io.camunda.easyrpa.connector.model.process;

import io.camunda.connector.generator.java.annotation.TemplateProperty;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public final class GetResultFromDocSet extends EasyRPAInput{

    @NotEmpty
    @TemplateProperty(group = "process", label = "id", description = "id doc set")
    private Integer idDocSet;

    @NotEmpty
    @TemplateProperty(group = "process", label = "name", description = "document name")
    private String docName;

    @NotEmpty
    @TemplateProperty(group = "process", label = "name", description = "document name")
    private String pathParam;
}
