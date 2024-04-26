package io.camunda.easyrpa.connector.services.process.impl;

import io.camunda.easyrpa.connector.constants.Constants;
import io.camunda.easyrpa.connector.converter.RequestConverter;
import io.camunda.easyrpa.connector.model.http.Authentication;
import io.camunda.easyrpa.connector.model.http.HttpAuthRequest;
import io.camunda.easyrpa.connector.model.http.enums.HttpMethod;
import io.camunda.easyrpa.connector.model.process.FindDataStore;
import io.camunda.easyrpa.connector.services.process.EasyRPAProcess;
import io.camunda.easyrpa.connector.model.http.HttpResult;

public class FindDataStoreService extends EasyRPAProcess {
    private final FindDataStore dataStore;

    public FindDataStoreService(FindDataStore dataStore) {
        this.dataStore = dataStore;
    }

    public Object invoke(String commonUrl, Authentication auth) throws Exception {
        String urlProcess = getUrlProcessRequest(commonUrl, Constants.FIND_DATA_STORE_URL);
        auth.setAuthTokenEndpoint(getUrlProcessRequest(commonUrl, Constants.AUTH_TOKEN_URL));
        HttpAuthRequest httpAuthRequest = RequestConverter.toHttpAuthRequest(auth, urlProcess,
                HttpMethod.GET, null, convertObjectToMap(dataStore));
        HttpResult httpResult = super.httpService.executeConnectorRequest(httpAuthRequest);
        return httpResult.getBody();
    }

}
