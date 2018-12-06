package com.transfermoney.config;

import com.transfermoney.service.transaction.Transaction;
import com.transfermoney.service.transaction.TransactionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Configuration class for Transaction
 */
@Configuration
public class TransactionConfig {

    @Autowired
    private Set<Transaction> transactions;

    @Bean
    public Map<TransactionType, Transaction> transactionMap() {
        return transactions.stream()
                .collect(Collectors.toMap(Transaction::getTransactionType, Function.identity()));
    }

}
