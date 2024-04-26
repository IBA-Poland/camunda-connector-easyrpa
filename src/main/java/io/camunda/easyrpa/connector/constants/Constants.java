package io.camunda.easyrpa.connector.constants;

public class Constants {

    public static final String GRANT_TYPE = "grant_type";
    public static final String GRANT_TYPE_VALUE = "client_credentials";
    public static final String CLIENT_ID = "client_id";
    public static final String CLIENT_SECRET = "client_secret";

    public static final String ACCESS_TOKEN = "access_token";
    public static final String VARIABLE_PATH_SEPARATOR = "\\.";
    public static final String PATH_VARIABLE = "{id}";
    public static final String PATH_PARAM ="columns";

    public static final String FILTER_PARAM_TYPE = "type";
    public static final String FILTER_PARAM_KEY = "key";
    public static final String FILTER_PARAM_PREDICATE = "predicate";
    public static final String FILTER_PARAM_VALUE = "value";
    public static final String FILTER_PARAM_VALUES = "values";
    public static final String FILTER_PARAM_COMPOSITIONS = "composition";
    public static final String FILTER_PARAM_FILTER = "filter";

    public static final String FILTER_VALUES_TYPE = "matcher";
    public static final String FILTER_VALUES_KEY = "name";
    public static final String FILTER_VALUES_PREDICATE = "CONTAINS";
    public static final String FILTER_TYPE = "qp";
    public static final String FILTER_COMPOSITION = "OR";

    public static final String CAMUNDA_MODEL_PROPERTY_NAME = "type";

    public static final String AUTH_TOKEN_URL = "/authrpa/oauth2/token";
    public static final String START_PROCESS_URL = "/api/v1/automation_processes/{id}/runs";
    public static final String GET_STATUS_PROCESS_URL = "/api/v1/runs/{id}";
    public static final String FIND_DATA_STORE_URL = "/api/v1/ds/by_name";
    public static final String GET_RESULT_FROM_DATA_STORE_URL = "/api/v1/ds/{id}/records/search";
    public static final String GET_RESULT_FROM_DOCUMENT_SET_URL = "/api/v1/doc_sets/{id}/records/search";


}
