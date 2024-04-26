package io.camunda.easyrpa.connector;

import io.camunda.connector.api.annotation.OutboundConnector;
import io.camunda.connector.api.outbound.OutboundConnectorContext;
import io.camunda.connector.api.outbound.OutboundConnectorFunction;
import io.camunda.connector.generator.java.annotation.ElementTemplate;
import io.camunda.easyrpa.connector.model.EasyRPARequest;
import io.camunda.easyrpa.connector.services.process.EasyRPAProcess;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@OutboundConnector(
    name = "EasyRPA connector",
    inputVariables = {"authentication", "commonUrl","process"},
    type = "io.camunda:template:1")
@ElementTemplate(
    id = "io.camunda.connector.EasyRPA",
    name = "EasyRPA connector",
    version = 1,
    description = "EasyRPA connector",
    icon = "icon.svg",
    inputDataClass = EasyRPARequest.class)
public class EasyRPAConnectorFunction implements OutboundConnectorFunction {

  private static final Logger LOGGER = LoggerFactory.getLogger(EasyRPAConnectorFunction.class);

  private final EasyRPAProcessFactory processFactory;

  public EasyRPAConnectorFunction() {
    processFactory = EasyRPAProcessFactory.getInstance();
  }

  @Override
  public Object execute(OutboundConnectorContext context) throws Exception {
    final var connectorRequest = context.bindVariables(EasyRPARequest.class);
    return executeConnector(connectorRequest);
  }

  private Object executeConnector(final EasyRPARequest request) throws Exception {
    EasyRPAProcess process = processFactory.createProcess(request.getProcess());
    LOGGER.info("Start process: " + process.toString());
    LOGGER.debug("Data: " + request.getClass().getName());
    return process.invoke(request.getCommonUrl(),request.getAuthentication());
  }
}
