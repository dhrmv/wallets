package com.wallets.api.base.web;

public interface ApiExceptionsConsts {

    // BadRequestException 400

    String INCORRECT_OPERATION_TYPE = "INCORRECT_OPERATION_TYPE";
    String INCORRECT_AMOUNT = "INCORRECT_AMOUNT";

    // NotFoundException 404

    String WALLET_NOT_FOUND = "WALLET_NOT_FOUND";

    // NotAcceptableException 406

    String INSUFFICIENT_FUNDS = "INSUFFICIENT_FUNDS";
}
