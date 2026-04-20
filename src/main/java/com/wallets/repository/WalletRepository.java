package com.wallets.repository;

import com.wallets.model.entity.Wallet;

import java.util.List;

public interface WalletRepository {

    Wallet findById(String walletId);

    List<Wallet> findAll();

    Wallet save(Wallet wallet);
}
