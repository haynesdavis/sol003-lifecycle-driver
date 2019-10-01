package com.accantosystems.stratoss.vnfmdriver.service;

import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.accantosystems.stratoss.vnfmdriver.driver.VNFLifecycleManagementDriver;
import com.accantosystems.stratoss.vnfmdriver.model.alm.ExecutionAcceptedResponse;
import com.accantosystems.stratoss.vnfmdriver.model.alm.ExecutionAsyncResponse;
import com.accantosystems.stratoss.vnfmdriver.model.alm.ExecutionRequest;
import com.accantosystems.stratoss.vnfmdriver.model.alm.ExecutionStatus;

@Service("LifecycleManagementService")
public class LifecycleManagementService {

    private final static Logger logger = LoggerFactory.getLogger(LifecycleManagementService.class);

    private final VNFLifecycleManagementDriver vnfLifecycleManagementDriver;
    private final MessageConversionService messageConversionService;
    private final ExternalMessagingService externalMessagingService;

    @Autowired
    public LifecycleManagementService(VNFLifecycleManagementDriver vnfLifecycleManagementDriver, MessageConversionService messageConversionService, ExternalMessagingService externalMessagingService) {
        this.vnfLifecycleManagementDriver = vnfLifecycleManagementDriver;
        this.messageConversionService = messageConversionService;
        this.externalMessagingService = externalMessagingService;
    }

    public ExecutionAcceptedResponse executeLifecycle(ExecutionRequest executionRequest) throws MessageConversionException {
        logger.info("Processing execution request");

        try {
            if ("Install".equalsIgnoreCase(executionRequest.getLifecycleName())) {
                // Generate CreateVnfRequest message
                final String createVnfRequest = messageConversionService.generateMessageFromRequest("CreateVnfRequest", executionRequest);
                // Send message to VNFM
                final String vnfInstanceResponse = vnfLifecycleManagementDriver.createVnfInstance(executionRequest.getDeploymentLocation(), createVnfRequest);
                // Convert response into properties to be returned to ALM
                final Map<String, String> outputs = messageConversionService.extractPropertiesFromMessage("VnfInstance", executionRequest, vnfInstanceResponse);

                final String requestId = UUID.randomUUID().toString();
                // TODO Need to put a delay into sending this (from a different thread) as this method needs to complete first (to send the response back to Brent)
                externalMessagingService.sendExecutionAsyncResponse(new ExecutionAsyncResponse(requestId, ExecutionStatus.COMPLETE, null, outputs));

                // Send response back to ALM
                return new ExecutionAcceptedResponse(requestId);
            } else if ("Configure".equalsIgnoreCase(executionRequest.getLifecycleName())) {
                // Instantiate
                final String vnfInstanceId = executionRequest.getProperties().get("vnfInstanceId");
                final String instantiateVnfRequest = messageConversionService.generateMessageFromRequest("InstantiateVnfRequest", executionRequest);
                final String requestId = vnfLifecycleManagementDriver.instantiateVnf(executionRequest.getDeploymentLocation(), vnfInstanceId, instantiateVnfRequest);
                return new ExecutionAcceptedResponse(requestId);
            } else if ("Start".equalsIgnoreCase(executionRequest.getLifecycleName())) {
                // Operate (Start)
                final String vnfInstanceId = executionRequest.getProperties().get("vnfInstanceId");
                final String operateVnfRequest = messageConversionService.generateMessageFromRequest("OperateVnfRequest-Start", executionRequest);
                final String requestId = vnfLifecycleManagementDriver.operateVnf(executionRequest.getDeploymentLocation(), vnfInstanceId, operateVnfRequest);
                return new ExecutionAcceptedResponse(requestId);
            } else if ("Stop".equalsIgnoreCase(executionRequest.getLifecycleName())) {
                // Operate (Stop)
                final String vnfInstanceId = executionRequest.getProperties().get("vnfInstanceId");
                final String operateVnfRequest = messageConversionService.generateMessageFromRequest("OperateVnfRequest-Stop", executionRequest);
                final String requestId = vnfLifecycleManagementDriver.operateVnf(executionRequest.getDeploymentLocation(), vnfInstanceId, operateVnfRequest);
                return new ExecutionAcceptedResponse(requestId);
            } else if ("Uninstall".equalsIgnoreCase(executionRequest.getLifecycleName())) {
                // Terminate
                final String vnfInstanceId = executionRequest.getProperties().get("vnfInstanceId");
                final String terminateVnfRequest = messageConversionService.generateMessageFromRequest("TerminateVnfRequest", executionRequest);
                final String requestId = vnfLifecycleManagementDriver.terminateVnf(executionRequest.getDeploymentLocation(), vnfInstanceId, terminateVnfRequest);
                return new ExecutionAcceptedResponse(requestId);
            } else if ("Reconfigure".equalsIgnoreCase(executionRequest.getLifecycleName())) {
                // Look at properties to see if there's anything changed to drive healing?
            } else {
                // Unsupported transition
            }
        } catch (MessageConversionException e) {
            logger.error("Error converting message", e);
            throw e;
        }

        return null;
    }

}