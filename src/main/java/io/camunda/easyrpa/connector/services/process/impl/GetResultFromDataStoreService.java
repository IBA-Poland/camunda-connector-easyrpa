package io.camunda.easyrpa.connector.services.process.impl;

import com.google.gson.JsonObject;
import io.camunda.easyrpa.connector.constants.Constants;
import io.camunda.easyrpa.connector.converter.RequestConverter;
import io.camunda.easyrpa.connector.model.http.Authentication;
import io.camunda.easyrpa.connector.model.http.HttpAuthRequest;
import io.camunda.easyrpa.connector.model.http.HttpResult;
import io.camunda.easyrpa.connector.model.http.enums.HttpMethod;
import io.camunda.easyrpa.connector.services.process.EasyRPAProcess;
import io.camunda.easyrpa.connector.model.process.GetResultFromDataStore;

import java.util.LinkedHashMap;

public class GetResultFromDataStoreService extends EasyRPAProcess {
    public static final String GET_ARRAY_RECORDS = "records";
    private final GetResultFromDataStore dataStore;

    public GetResultFromDataStoreService(GetResultFromDataStore dataStore) {
        this.dataStore = dataStore;
    }

    public Object invoke(String commonUrl, Authentication auth) throws Exception {
        String urlProcess = getUrlProcessRequest(commonUrl, Constants.GET_RESULT_FROM_DATA_STORE_URL,
                dataStore.getIdDataStore());
        auth.setAuthTokenEndpoint(getUrlProcessRequest(commonUrl, Constants.AUTH_TOKEN_URL));
        LinkedHashMap<String,Object> filter =
                RequestConverter.getFilter(Constants.FILTER_VALUES_KEY,dataStore.getDocName());
        HttpAuthRequest httpAuthRequest = RequestConverter.toHttpAuthRequest(auth, urlProcess,
                HttpMethod.POST, filter, null);
        HttpResult httpResult = httpService.executeConnectorRequest(httpAuthRequest);
        return getResultByKeys(httpResult.getBody());
    }

    public Object getResultByKeys(Object body){
        JsonObject jsonResp = new JsonObject();
        responseConverter.extractResponseParams(body,GET_ARRAY_RECORDS,jsonResp);
        return responseConverter.convertToResponse(jsonResp);
    }
}
