package io.camunda.easyrpa.connector.services.process;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.gson.Gson;
import io.camunda.easyrpa.connector.services.http.HttpService;
import io.camunda.easyrpa.connector.constants.Constants;
import io.camunda.easyrpa.connector.converter.ResponseConverter;
import io.camunda.easyrpa.connector.model.http.Authentication;

import java.util.Map;

public abstract class EasyRPAProcess {

    private final HttpTransport httpTransport = new NetHttpTransport();
    private final HttpRequestFactory requestFactory = httpTransport.createRequestFactory();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Gson gson = new Gson();
    protected final HttpService httpService = new HttpService(objectMapper, requestFactory);
    protected final ResponseConverter responseConverter = new ResponseConverter(gson,objectMapper);

    public abstract Object invoke(String commonUrl, Authentication auth) throws Exception;

    protected String getUrlProcessRequest(String commonUrl, String urlProcess, Integer id) {
        return commonUrl + urlProcess.replace(Constants.PATH_VARIABLE, id.toString());
    }

    protected String getUrlProcessRequest(String commonUrl, String urlProcess) {
        return commonUrl + urlProcess;
    }

    protected Map<String,String> convertObjectToMap(Object object){
        Map<String,String> mapPathParam = objectMapper.convertValue(object, new TypeReference<>(){});
        mapPathParam.remove(Constants.CAMUNDA_MODEL_PROPERTY_NAME);
        return mapPathParam;
    }
}
