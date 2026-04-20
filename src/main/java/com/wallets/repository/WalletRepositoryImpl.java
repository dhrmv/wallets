package com.wallets.repository;

import com.wallets.model.entity.Wallet;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
@Slf4j
public class WalletRepositoryImpl implements WalletRepository{

    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Wallet> WALLET_ROW_MAPPER = (rs, i) -> {

        Wallet wallet = new Wallet();

        wallet.setId(rs.getString("id"));
        wallet.setBalance(rs.getLong("balance"));

        return wallet;
    };

    @Override
    public Wallet findById(String walletId) {

        List<Wallet> wallets = jdbcTemplate.query(
                "SELECT w.* FROM wallets w WHERE w.id = ?;",
                WALLET_ROW_MAPPER,
                walletId
        );

        return wallets.isEmpty() ? null : wallets.get(0);
    }

    @Override
    public List<Wallet> findAll() {

        List<Wallet> wallets = jdbcTemplate.query(
                "SELECT w.* FROM wallets w;",
                WALLET_ROW_MAPPER
        );

        return wallets;
    }

    @Override
    public Wallet save(Wallet wallet) {

        final String INSERT_SQL = "INSERT INTO wallets(id, balance) " +
                "VALUES (?, ?) " +
                "ON CONFLICT (id) DO UPDATE SET balance = ?;";

        jdbcTemplate.update(
                INSERT_SQL,
                wallet.getId(),
                wallet.getBalance(),
                wallet.getBalance()
        );

        return wallet;
    }
}
