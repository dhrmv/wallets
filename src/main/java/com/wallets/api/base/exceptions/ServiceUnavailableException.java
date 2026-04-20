package com.wallets.api.base.exceptions;

public class ServiceUnavailableException extends ApiException {
    public ServiceUnavailableException(){
    }
    public ServiceUnavailableException(String message) {
        super(message);
    }
}
