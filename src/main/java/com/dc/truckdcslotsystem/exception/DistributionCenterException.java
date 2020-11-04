package com.dc.truckdcslotsystem.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class DistributionCenterException extends RuntimeException {
    public DistributionCenterException(String message) {
        super(message);
    }
}
