package com.transfermoney.model;

import com.transfermoney.service.transaction.TransactionType;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;

/**
 * This class is the model of request for transactions.
 */
public class UserTransaction {

    @ApiModelProperty(notes = "The user's account number")
    private long accountNumber;

    @ApiModelProperty(notes = "The amount of the transaction")
    private BigDecimal amount;

    @ApiModelProperty(notes = "The type of transaction")
    private TransactionType transactionType;

    @ApiModelProperty(notes = "The destination account that Will be used on TRANSFER transaction")
    private long toAccountNumber;

    public long getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(long accountNumber) {
        this.accountNumber = accountNumber;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public long getToAccountNumber() {
        return toAccountNumber;
    }

    public void setToAccountNumber(long toAccountNumber) {
        this.toAccountNumber = toAccountNumber;
    }
}
