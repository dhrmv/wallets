package com.wallets.service;

import com.wallets.model.dto.WalletOperationRequest;
import com.wallets.model.entity.Wallet;

import java.util.List;

public interface WalletService {

    Wallet createWallet();
    Wallet findWalletById(String walletId);
    List<Wallet> findAll();
    Wallet operation(WalletOperationRequest request);
}
