package io.camunda.easyrpa.connector.services.process.impl;

import com.google.gson.JsonObject;
import io.camunda.easyrpa.connector.model.http.HttpAuthRequest;
import io.camunda.easyrpa.connector.model.http.enums.HttpMethod;
import io.camunda.easyrpa.connector.services.process.EasyRPAProcess;
import io.camunda.easyrpa.connector.constants.Constants;
import io.camunda.easyrpa.connector.converter.RequestConverter;
import io.camunda.easyrpa.connector.model.http.Authentication;
import io.camunda.easyrpa.connector.model.http.HttpResult;
import io.camunda.easyrpa.connector.model.process.StartProcess;

public class StartProcessService extends EasyRPAProcess {
    public static final String GET_RUN_ID_KEY = "id";
    private final StartProcess model;

    public StartProcessService(final StartProcess model) {
        this.model = model;
    }

    public Object invoke(String commonUrl, Authentication auth) throws Exception {
        String urlProcess = getUrlProcessRequest(commonUrl, Constants.START_PROCESS_URL, model.getId());
        auth.setAuthTokenEndpoint(getUrlProcessRequest(commonUrl, Constants.AUTH_TOKEN_URL));
        HttpAuthRequest httpAuthRequest = RequestConverter.toHttpAuthRequest(auth, urlProcess, HttpMethod.POST,
                null, null);
            HttpResult httpResult = super.httpService.executeConnectorRequest(httpAuthRequest);
            return getResultByKeys(httpResult.getBody());
    }

    public Object getResultByKeys(Object body){
        JsonObject jsonResp = new JsonObject();
        responseConverter.extractResponseParams(body,GET_RUN_ID_KEY,jsonResp);
        return responseConverter.convertToResponse(jsonResp);
    }
}
