package io.camunda.easyrpa.connector.model.process;

import io.camunda.connector.generator.java.annotation.TemplateProperty;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public final class StartProcess extends EasyRPAInput {

    @NotEmpty
    @TemplateProperty(group = "process", label = "id", description = "process_id")
    private Integer id;
}
