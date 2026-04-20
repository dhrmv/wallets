package com.wallets.controller;

import com.wallets.api.base.web.ApiResponse;
import com.wallets.model.dto.WalletOperationRequest;
import com.wallets.model.entity.Wallet;
import com.wallets.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1")
@RequiredArgsConstructor
public class WalletController {

    private final WalletService walletService;

    @PostMapping("/create")
    public ApiResponse<Wallet> createWallet() {

        Wallet wallet = walletService.createWallet();

        return ApiResponse.ok(wallet);
    }

    @GetMapping("/wallets")
    public ApiResponse<List<Wallet>> getWallets() {

        List<Wallet> wallets = walletService.findAll();

        return ApiResponse.ok(wallets);
    }

    @GetMapping("/wallets/{id}")
    public ApiResponse<Wallet> getWallet(@PathVariable String id) {

        Wallet wallet = walletService.findWalletById(id);

        return ApiResponse.ok(wallet);
    }

    @PostMapping("/wallet")
    public ApiResponse<Wallet> walletOperation(@RequestBody WalletOperationRequest request) {

        Wallet wallet = walletService.operation(request);

        return ApiResponse.ok(wallet);
    }
}
