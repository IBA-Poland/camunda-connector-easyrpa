package io.camunda.easyrpa.connector.converter;

import io.camunda.easyrpa.connector.constants.Constants;
import io.camunda.easyrpa.connector.model.http.Authentication;
import io.camunda.easyrpa.connector.model.http.HttpAuthRequest;
import io.camunda.easyrpa.connector.model.http.enums.HttpMethod;
import io.camunda.easyrpa.connector.model.http.HttpRequestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class RequestConverter {

    private RequestConverter() {}

    public static HttpRequestTemplate toHttpRequestTemplate(HttpAuthRequest request) {
        Map<String, String> authParam = new HashMap<>();
        authParam.put(Constants.GRANT_TYPE, Constants.GRANT_TYPE_VALUE);
        authParam.put(Constants.CLIENT_ID, request.getAuthentication().getClientId());
        authParam.put(Constants.CLIENT_SECRET, request.getAuthentication().getClientSecret());
        return new HttpRequestTemplate(
                request.getAuthentication().getAuthTokenEndpoint(),
                HttpMethod.POST,
                null,
                authParam
        );
    }

    public static HttpAuthRequest toHttpAuthRequest(Authentication auth, String url, HttpMethod httpMethod,
                                                    Object body,
                                                    Map<String, String> queryParameters) {
        HttpRequestTemplate reqTemp = new HttpRequestTemplate(url, httpMethod, body, queryParameters);
        return new HttpAuthRequest(auth, reqTemp);
    }

    public static LinkedHashMap<String,Object> getFilter(String filterName, String filterValue) {
        LinkedHashMap<String,String> mapParam = new LinkedHashMap<>();
        mapParam.put(Constants.FILTER_PARAM_TYPE,Constants.FILTER_VALUES_TYPE);
        mapParam.put(Constants.FILTER_PARAM_KEY,filterName);
        mapParam.put(Constants.FILTER_PARAM_PREDICATE,Constants.FILTER_VALUES_PREDICATE);
        mapParam.put(Constants.FILTER_PARAM_VALUE,filterValue);

        ArrayList<Object> values = new ArrayList<Object>();
        values.add(mapParam);

        LinkedHashMap<String,Object> filterParam = new LinkedHashMap<>();
        filterParam.put(Constants.FILTER_PARAM_TYPE, Constants.FILTER_TYPE);
        filterParam.put(Constants.FILTER_PARAM_COMPOSITIONS, Constants.FILTER_COMPOSITION);
        filterParam.put(Constants.FILTER_PARAM_VALUES,values);

        LinkedHashMap<String,Object> filter = new LinkedHashMap<>();
        filter.put(Constants.FILTER_PARAM_FILTER, filterParam);

        return filter;
    }
}
