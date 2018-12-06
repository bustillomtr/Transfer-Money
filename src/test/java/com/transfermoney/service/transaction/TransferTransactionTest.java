package com.transfermoney.service.transaction;

import com.transfermoney.model.Account;
import com.transfermoney.repository.AccountRepository;
import com.transfermoney.util.Constant;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.Arrays;

@RunWith(MockitoJUnitRunner.class)
public class TransferTransactionTest {

    @InjectMocks
    private TransferTransaction transaction;

    @Mock
    private AccountRepository accountRepository;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void validTransaction() {
        Account account = buildValidAccount(1001L);
        Account destinationAccount = buildValidAccount(1002L);

        Mockito.when(accountRepository.saveAll(ArgumentMatchers.anyIterable())).thenReturn(Arrays.asList(account, destinationAccount));

        TransactionResult transactionResult = transaction.processTransaction(
                buildTransactionContext(account, new BigDecimal(100.00), destinationAccount));

        Assert.assertNotNull(transactionResult);
        Assert.assertTrue(transactionResult.isSuccess());
        Assert.assertEquals(Constant.MSG_TRXN_SUCCESSFUL, transactionResult.getMessage());
    }

    @Test
    public void invalidTransactionDestinationAccountIsNotFound() {
        TransactionResult transactionResult = transaction.processTransaction(
                buildTransactionContext(buildValidAccount(1001L), new BigDecimal(100.00), null));

        Assert.assertNotNull(transactionResult);
        Assert.assertFalse(transactionResult.isSuccess());
        Assert.assertEquals(Constant.ERR_MSG_DEST_ACCOUNT_NOT_FOUND, transactionResult.getMessage());
    }

    @Test
    public void invalidTransactionInsufficientFund() {
        Account account = buildValidAccount(1001L);
        Account destinationAccount = buildValidAccount(1002L);

        TransactionResult transactionResult = transaction.processTransaction(
                buildTransactionContext(account, new BigDecimal(1000.00), destinationAccount));

        Assert.assertNotNull(transactionResult);
        Assert.assertFalse(transactionResult.isSuccess());
        Assert.assertEquals(Constant.ERR_MSG_INSUFFICIENT_FUND, transactionResult.getMessage());
    }

    private TransactionContext buildTransactionContext(Account account, BigDecimal amount, Account destinationAccount) {
        return new TransactionContext(account, amount, destinationAccount);
    }

    private Account buildValidAccount(long accountNumber) {
        Account account = new Account();
        account.setuId(1L);
        account.setBalance(new BigDecimal(100.00));
        account.setAccountNumber(accountNumber);
        return account;
    }

}
