package com.mukul.customactuator;

import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.annotation.WriteOperation;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Endpoint(id = "customHealth")
public class CustomHealthEndpoint {

    private String status = "UP";

    @ReadOperation
    public Map<String, String> getHealthStatus() {
        Map<String, String> response = new HashMap<>();
        response.put("customStatus", status);
        return response;
    }

    @WriteOperation
    public void setHealthStatus(String newStatus) {
        this.status = newStatus;
    }
}