package com.wallets.api.base.exceptions;

public class NotFoundException extends ApiException {
    public NotFoundException() {
    }
    public NotFoundException(String message) {
        super(message);
    }
}
