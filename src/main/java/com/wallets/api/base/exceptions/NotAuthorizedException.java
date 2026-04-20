package com.wallets.api.base.exceptions;

public class NotAuthorizedException extends ApiException {
    public NotAuthorizedException() {
    }
    public NotAuthorizedException(String message) {
        super(message);
    }
}
