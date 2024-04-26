package io.camunda.easyrpa.connector;

import io.camunda.easyrpa.connector.model.process.*;
import io.camunda.easyrpa.connector.services.process.EasyRPAProcess;
import io.camunda.easyrpa.connector.services.process.impl.*;
import lombok.Getter;

public class EasyRPAProcessFactory {

    @Getter
    private static final EasyRPAProcessFactory instance = new EasyRPAProcessFactory();

    private EasyRPAProcessFactory() {}

    public EasyRPAProcess createProcess(EasyRPAInput input) {
        return switch (input) {
            case ProcessStatus in -> new ProcessStatusService(in);
            case StartProcess in -> new StartProcessService(in);
            case FindDataStore in -> new FindDataStoreService(in);
            case GetResultFromDataStore in -> new GetResultFromDataStoreService(in);
            case GetResultFromDocSet in -> new GetResultFromDocSetService(in);
            case null, default -> throw new UnsupportedOperationException("Unsupported process : " + input);
        };
    }
}
