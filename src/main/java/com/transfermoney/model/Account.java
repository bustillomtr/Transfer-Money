package com.transfermoney.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
public class Account {

    @Id
    @GeneratedValue
    @JsonIgnore
    private long id;

    @NotNull
    @JsonIgnore
    private long uId;

    @NotNull
    @JsonProperty(required = true)
    private long accountNumber;

    @JsonProperty(required = true)
    private BigDecimal balance;

    public long getId() {
        return id;
    }

    public long getuId() {
        return uId;
    }

    public void setuId(long uId) {
        this.uId = uId;
    }

    public long getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(long accountNumber) {
        this.accountNumber = accountNumber;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}
