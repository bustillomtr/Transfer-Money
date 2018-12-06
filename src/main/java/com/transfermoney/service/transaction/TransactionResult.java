package com.transfermoney.service.transaction;

import com.transfermoney.model.Account;

/**
 * Class for storing the result of the transaction.
 */
public class TransactionResult {

    private Account account;

    private String message;

    private boolean success;

    public TransactionResult(String message, boolean success) {
        this.account = null;
        this.message = message;
        this.success = success;
    }

    public TransactionResult(Account account, String message, boolean success) {
        this.account = account;
        this.message = message;
        this.success = success;
    }

    public Account getAccount() {
        return account;
    }

    public String getMessage() {
        return message;
    }

    public boolean isSuccess() {
        return success;
    }
}
