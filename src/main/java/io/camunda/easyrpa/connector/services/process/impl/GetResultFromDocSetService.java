package io.camunda.easyrpa.connector.services.process.impl;

import com.google.gson.JsonObject;
import io.camunda.easyrpa.connector.constants.Constants;
import io.camunda.easyrpa.connector.model.http.Authentication;
import io.camunda.easyrpa.connector.model.http.HttpAuthRequest;
import io.camunda.easyrpa.connector.model.http.enums.HttpMethod;
import io.camunda.easyrpa.connector.services.process.EasyRPAProcess;
import io.camunda.easyrpa.connector.converter.RequestConverter;
import io.camunda.easyrpa.connector.model.http.HttpResult;
import io.camunda.easyrpa.connector.model.process.GetResultFromDocSet;

import java.util.LinkedHashMap;

public class GetResultFromDocSetService extends EasyRPAProcess {
    public static final String GET_ARRAY_RECORDS = "records";
    private final GetResultFromDocSet docSet;

    public GetResultFromDocSetService(GetResultFromDocSet docSet) {
        this.docSet = docSet;
    }

    public Object invoke(String commonUrl, Authentication auth) throws Exception {
        String urlProcess = getUrlProcessRequest(commonUrl, Constants.GET_RESULT_FROM_DOCUMENT_SET_URL,
                docSet.getIdDocSet());
        auth.setAuthTokenEndpoint(getUrlProcessRequest(commonUrl, Constants.AUTH_TOKEN_URL));
        LinkedHashMap<String,Object> filter =
                RequestConverter.getFilter(Constants.FILTER_VALUES_KEY,docSet.getDocName());
        LinkedHashMap<String,String> pathParam = new LinkedHashMap<>();
        pathParam.put(Constants.PATH_PARAM, docSet.getPathParam());
        HttpAuthRequest httpAuthRequest = RequestConverter.toHttpAuthRequest(auth, urlProcess,
                HttpMethod.POST, filter, pathParam);
        HttpResult httpResult = httpService.executeConnectorRequest(httpAuthRequest);
        return getResultByKeys(httpResult.getBody());
    }

    public Object getResultByKeys(Object body){
        JsonObject jsonResp = new JsonObject();
        responseConverter.extractResponseParams(body,GET_ARRAY_RECORDS,jsonResp);
        return responseConverter.convertToResponse(jsonResp);
    }
}
