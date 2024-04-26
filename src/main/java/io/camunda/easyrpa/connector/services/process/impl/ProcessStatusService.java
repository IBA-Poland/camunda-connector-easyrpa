package io.camunda.easyrpa.connector.services.process.impl;

import com.google.gson.JsonObject;
import io.camunda.easyrpa.connector.constants.Constants;
import io.camunda.easyrpa.connector.converter.RequestConverter;
import io.camunda.easyrpa.connector.model.http.Authentication;
import io.camunda.easyrpa.connector.model.http.HttpAuthRequest;
import io.camunda.easyrpa.connector.model.http.enums.HttpMethod;
import io.camunda.easyrpa.connector.services.process.EasyRPAProcess;
import io.camunda.easyrpa.connector.model.http.HttpResult;
import io.camunda.easyrpa.connector.model.process.ProcessStatus;

public class ProcessStatusService extends EasyRPAProcess {
    public static final String GET_ID_KEY = "id";
    public static final String GET_STATUS_KEY = "status";
    private final ProcessStatus model;

    public ProcessStatusService(ProcessStatus model) {
        this.model = model;
    }

    public Object invoke(String commonUrl, Authentication auth) throws Exception {
        String urlProcess = getUrlProcessRequest(commonUrl, Constants.GET_STATUS_PROCESS_URL, model.getId());
        auth.setAuthTokenEndpoint(getUrlProcessRequest(commonUrl, Constants.AUTH_TOKEN_URL));
        HttpAuthRequest httpAuthRequest = RequestConverter.toHttpAuthRequest(auth, urlProcess,
                HttpMethod.GET, null, null);
        HttpResult httpResult = super.httpService.executeConnectorRequest(httpAuthRequest);
        return getResultByKeys(httpResult.getBody());
    }

    public Object getResultByKeys(Object body){
        JsonObject jsonResp = new JsonObject();
        responseConverter.extractResponseParams(body,GET_ID_KEY,jsonResp);
        responseConverter.extractResponseParams(body,GET_STATUS_KEY,jsonResp);
        return responseConverter.convertToResponse(jsonResp);
    }
}
