package io.camunda.easyrpa.connector.model.http.error;

import lombok.Data;

@Data
public class ErrorResponse {

    private String error;
    private String errorCode;
}
