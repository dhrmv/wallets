package com.wallets.model.dto;

import lombok.Data;

@Data
public class WalletOperationRequest {

    private String walletId;
    private String operationType;
    private Integer amount;
}
