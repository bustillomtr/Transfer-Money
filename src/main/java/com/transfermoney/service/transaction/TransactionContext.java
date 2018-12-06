package com.transfermoney.service.transaction;

import com.transfermoney.model.Account;

import java.math.BigDecimal;

/**
 * Class to hold the context of the transaction.
 */
public class TransactionContext {

    private Account account;

    private BigDecimal amount;

    private Account destinationAccount;

    public TransactionContext(Account account, BigDecimal amount) {
        this.account = account;
        this.amount = amount;
    }

    public TransactionContext(Account account, BigDecimal amount, Account destinationAccount) {
        this.account = account;
        this.amount = amount;
        this.destinationAccount = destinationAccount;
    }

    public Account getAccount() {
        return account;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Account getDestinationAccount() {
        return destinationAccount;
    }

}
