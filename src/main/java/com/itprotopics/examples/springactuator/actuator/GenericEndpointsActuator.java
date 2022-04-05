package com.itprotopics.examples.springactuator.actuator;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;

@Endpoint(id = "genericEndPoints")
public class GenericEndpointsActuator {
    
    @ReadOperation
    public CustomHealth genericEndPoints() {

        Map<String, Object> details = new LinkedHashMap<>();
        details.put("genericEndPoints", "Everything looks good");

        validarRenapo(details);
        validarCodigosPostales(details);

        CustomHealth health = new CustomHealth();
        health.setHealthDetails(details);
        return health;

    }

    private void validarRenapo (Map<String, Object> details)  {
        // Validar el endpont de renapo
        details.put("renapo", "UP");

    }

    private void validarCodigosPostales (Map<String, Object> details)  {
        // Validar el endpont de c'odigos 
        details.put("codigosPostales", "DOWN");

    }

}