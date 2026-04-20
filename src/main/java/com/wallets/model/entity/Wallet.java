package com.wallets.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "wallets")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Wallet {
        @Id
        private String id;
        private Long balance;

        public void deposit(long amount) {
                this.balance = this.balance + amount;
        }

        public void withdraw(long amount) {
                this.balance = this.balance - amount;
        }
}
