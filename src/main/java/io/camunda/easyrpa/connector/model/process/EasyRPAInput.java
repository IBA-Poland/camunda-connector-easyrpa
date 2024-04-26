package io.camunda.easyrpa.connector.model.process;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = ProcessStatus.class, name = "processStatus"),
        @JsonSubTypes.Type(value = StartProcess.class, name = "startProcess"),
        @JsonSubTypes.Type(value = FindDataStore.class, name = "findDataStore"),
        @JsonSubTypes.Type(value = GetResultFromDataStore.class, name = "searchDataStore"),
        @JsonSubTypes.Type(value = GetResultFromDocSet.class, name = "searchDocSet")

})
public abstract class EasyRPAInput{
}
