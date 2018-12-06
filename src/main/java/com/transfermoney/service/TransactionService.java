package com.transfermoney.service;

import com.transfermoney.integration.ApplicationRestClient;
import com.transfermoney.model.Account;
import com.transfermoney.model.ResponseResult;
import com.transfermoney.model.UserTransaction;
import com.transfermoney.repository.AccountRepository;
import com.transfermoney.service.transaction.Transaction;
import com.transfermoney.service.transaction.TransactionContext;
import com.transfermoney.service.transaction.TransactionResult;
import com.transfermoney.service.transaction.TransactionType;
import com.transfermoney.util.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;

/**
 * Service class for transactions.
 */
@Service
public class TransactionService {

    private Logger logger = LoggerFactory.getLogger(TransactionService.class);

    @Autowired
    private Map<TransactionType, Transaction> transactionMap;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ApplicationRestClient applicationRestClient;

    @Value("${rest.get.account.balance.url}")
    private String accountBalanceUrl;

    public ResponseResult processTransaction(UserTransaction userTransaction) {
        if (StringUtils.isEmpty(userTransaction.getTransactionType()) &&
                userTransaction.getAccountNumber() == 0 &&
                userTransaction.getAmount() == null) {
            return new ResponseResult(Constant.ERR_MSG_INVALID_REQUEST);
        }

        logger.info("Start processing transaction.");

        Account account = accountRepository.findByAccountNumber(userTransaction.getAccountNumber());
        if (account == null) {
            return new ResponseResult(Constant.ERR_MSG_ACCOUNT_NOT_FOUND);
        }

        BigDecimal amount = userTransaction.getAmount();
        if (amount.doubleValue() <= 0) {
            return new ResponseResult(Constant.ERR_MSG_INVALID_AMOUNT);
        }

        Account destinationAccount = null;
        if (userTransaction.getToAccountNumber() != 0) {
            destinationAccount = accountRepository.findByAccountNumber(userTransaction.getToAccountNumber());
        }

        TransactionResult transactionResult;
        Transaction transaction = transactionMap.get(userTransaction.getTransactionType());
        if (transaction == null) {
            return new ResponseResult("Unknown transaction!");
        }

        // invoke transaction process method
        transactionResult = transaction.process(new TransactionContext(account, amount, destinationAccount));

        return getResponseResultByTransactionResult(transactionResult);
    }

    /**
     * Gets ResponseResult object by transaction result.
     *
     * @param transactionResult
     * @return
     */
    private ResponseResult getResponseResultByTransactionResult(TransactionResult transactionResult) {
        if (transactionResult == null) {
            return new ResponseResult("Invalid transaction!");
        } else {
            if (transactionResult.isSuccess()) {
                // if success, call rest api via HTTP to get updated balance
                try {
                    ResponseResult responseResult = applicationRestClient.callGetRestApi(accountBalanceUrl + transactionResult.getAccount().getAccountNumber());
                    if (responseResult.isValid()) {
                        transactionResult.getAccount().setBalance(responseResult.getAccount().getBalance());
                        return new ResponseResult(transactionResult.getAccount(), transactionResult.getMessage());
                    } else {
                        return new ResponseResult(responseResult.getErrorMessage());
                    }
                } catch (IOException e) {
                    logger.error("Error encountered!", e);
                    return new ResponseResult(String.format("Error encountered! %s", e.getMessage()));
                }
            } else {
                return new ResponseResult(transactionResult.getMessage());
            }
        }
    }

}
