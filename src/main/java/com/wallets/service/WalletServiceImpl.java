package com.wallets.service;

import com.wallets.api.base.exceptions.BadRequestException;
import com.wallets.api.base.exceptions.NotAcceptableException;
import com.wallets.api.base.exceptions.NotFoundException;
import com.wallets.api.base.web.ApiExceptionsConsts;
import com.wallets.repository.WalletRepository;
import com.wallets.model.dto.WalletOperationRequest;
import com.wallets.model.entity.OperationType;
import com.wallets.model.entity.Wallet;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class WalletServiceImpl implements WalletService {

    private final WalletRepository walletRepository;

    @Override
    public Wallet createWallet() {

        Wallet newWallet = new Wallet();

        String walletId = UUID.randomUUID().toString();

        newWallet.setId(walletId);
        newWallet.setBalance(0l);

        return walletRepository.save(newWallet);
    }

    @Override
    public Wallet findWalletById(String walletId) {

        Wallet wallet = walletRepository.findById(walletId);

        if (wallet == null) {
            throw new NotFoundException(ApiExceptionsConsts.WALLET_NOT_FOUND);
        }

        return wallet;
    }

    @Override
    public List<Wallet> findAll() {

        List<Wallet> wallets = walletRepository.findAll();

        return wallets;
    }

    @Override
    public Wallet operation(WalletOperationRequest request) {

        OperationType operationType;
        Integer amount = request.getAmount();

        try {
            operationType = OperationType.valueOf(request.getOperationType());
        } catch (Exception e) {
            throw new BadRequestException(ApiExceptionsConsts.INCORRECT_OPERATION_TYPE);
        }

        if (amount <= 0) {
            throw new BadRequestException(ApiExceptionsConsts.INCORRECT_AMOUNT);
        }

        Wallet wallet = walletRepository.findById(request.getWalletId());

        if (wallet == null) {
            throw new NotFoundException(ApiExceptionsConsts.WALLET_NOT_FOUND);
        }

        Long balance = wallet.getBalance();

        if (operationType.equals(OperationType.DEPOSIT)) {
            wallet.setBalance(balance + amount);
        }

        if (operationType.equals(OperationType.WITHDRAW)) {
            if (wallet.getBalance() - amount < 0) {
                throw new NotAcceptableException(ApiExceptionsConsts.INSUFFICIENT_FUNDS);
            }
            wallet.setBalance(balance - amount);
        }

        return walletRepository.save(wallet);
    }
}
