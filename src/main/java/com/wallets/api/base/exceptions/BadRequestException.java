package com.wallets.api.base.exceptions;

public class BadRequestException extends ApiException {

    public BadRequestException() {
    }

    public BadRequestException(String message) {
        super(message);
    }
}

