package com.itprotopics.examples.springactuator.actuator;

import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;

@Endpoint(id = "vigencia")
public class VigenciaActuator {
    
    @ReadOperation
    public String vigencia() {
        return "IS UP";
    }
}