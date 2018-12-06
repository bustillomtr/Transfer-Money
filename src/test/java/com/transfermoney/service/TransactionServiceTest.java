package com.transfermoney.service;

import com.transfermoney.integration.ApplicationRestClient;
import com.transfermoney.model.Account;
import com.transfermoney.model.ResponseResult;
import com.transfermoney.model.UserTransaction;
import com.transfermoney.repository.AccountRepository;
import com.transfermoney.service.transaction.*;
import com.transfermoney.util.Constant;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;

@RunWith(MockitoJUnitRunner.class)
public class TransactionServiceTest {

    @InjectMocks
    private TransactionService transactionService;

    @Mock
    private AccountRepository accountRepository;
    @Mock
    private DepositTransaction depositTransaction;
    @Mock
    private WithdrawTransaction withdrawTransaction;
    @Mock
    private TransferTransaction transferTransaction;

    @Mock
    private ApplicationRestClient applicationRestClient;

    @Mock
    private Map<TransactionType, Transaction> transactionMap;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);

        Mockito.when(transactionMap.get(TransactionType.DEPOSIT)).thenReturn(depositTransaction);
        Mockito.when(transactionMap.get(TransactionType.WITHDRAW)).thenReturn(withdrawTransaction);
        Mockito.when(transactionMap.get(TransactionType.TRANSFER)).thenReturn(transferTransaction);
    }

    @Test
    public void processTransactionWhenAccountIsNotExistent() {

        Mockito.when(accountRepository.findByAccountNumber(ArgumentMatchers.anyLong())).thenReturn(null);

        UserTransaction userTransaction = buildUserTransaction(0L, new BigDecimal(100.00), TransactionType.DEPOSIT, 0L);

        ResponseResult responseResult = transactionService.processTransaction(userTransaction);

        Assert.assertNotNull(responseResult);
        Assert.assertFalse(responseResult.isValid());
        Assert.assertEquals(Constant.ERR_MSG_ACCOUNT_NOT_FOUND, responseResult.getErrorMessage());
    }

    @Test
    public void processTransactionWhenAmountIsLessThanOrEqualToZero() {

        Mockito.when(accountRepository.findByAccountNumber(ArgumentMatchers.anyLong())).thenReturn(new Account());

        UserTransaction userTransaction = buildUserTransaction(0L, new BigDecimal(0.00), TransactionType.DEPOSIT, 0L);

        ResponseResult responseResult = transactionService.processTransaction(userTransaction);

        Assert.assertNotNull(responseResult);
        Assert.assertFalse(responseResult.isValid());
        Assert.assertEquals(Constant.ERR_MSG_INVALID_AMOUNT, responseResult.getErrorMessage());
    }

    @Test
    public void processTransactionWhenContextIsNull() {

        Mockito.when(accountRepository.findByAccountNumber(ArgumentMatchers.anyLong())).thenReturn(new Account());
        Mockito.when(depositTransaction.process(ArgumentMatchers.any())).thenReturn(new TransactionResult(Constant.ERR_MSG_INVALID_REQUEST, false));

        UserTransaction userTransaction = buildUserTransaction(1001L, new BigDecimal(10.00), TransactionType.DEPOSIT, 1002L);

        ResponseResult responseResult = transactionService.processTransaction(userTransaction);

        Assert.assertNotNull(responseResult);
        Assert.assertFalse(responseResult.isValid());
        Assert.assertEquals(Constant.ERR_MSG_INVALID_REQUEST, responseResult.getErrorMessage());
    }

    @Test
    public void processValidDepositTransaction() throws IOException {

        Account validAccount = buildValidAccount();

        Mockito.when(accountRepository.findByAccountNumber(ArgumentMatchers.anyLong())).thenReturn(validAccount);

        TransactionResult transactionResult = validTransactionResult();

        Mockito.when(depositTransaction.process(ArgumentMatchers.any())).thenReturn(transactionResult);

        UserTransaction userTransaction = buildUserTransaction(1001L, new BigDecimal(10.00), TransactionType.DEPOSIT, 0L);

        Mockito.when(applicationRestClient.callGetRestApi(ArgumentMatchers.anyString())).thenReturn(validResponseResult());

        ResponseResult responseResult = transactionService.processTransaction(userTransaction);

        Assert.assertNotNull(responseResult);
        Assert.assertTrue(responseResult.isValid());
    }

    @Test
    public void processValidWithdrawTransaction() throws IOException {

        Account validAccount = buildValidAccount();

        Mockito.when(accountRepository.findByAccountNumber(ArgumentMatchers.anyLong())).thenReturn(validAccount);

        TransactionResult transactionResult = validTransactionResult();

        Mockito.when(withdrawTransaction.process(ArgumentMatchers.any())).thenReturn(transactionResult);

        UserTransaction userTransaction = buildUserTransaction(1001L, new BigDecimal(10.00), TransactionType.WITHDRAW, 0L);

        Mockito.when(applicationRestClient.callGetRestApi(ArgumentMatchers.anyString())).thenReturn(validResponseResult());

        ResponseResult responseResult = transactionService.processTransaction(userTransaction);

        Assert.assertNotNull(responseResult);
        Assert.assertTrue(responseResult.isValid());
    }

    @Test
    public void processValidTransferTransaction() throws IOException {

        Account validAccount = buildValidAccount();

        Mockito.when(accountRepository.findByAccountNumber(ArgumentMatchers.anyLong())).thenReturn(validAccount);

        TransactionResult transactionResult = validTransactionResult();

        Mockito.when(transferTransaction.process(ArgumentMatchers.any())).thenReturn(transactionResult);

        UserTransaction userTransaction = buildUserTransaction(1001L, new BigDecimal(10.00), TransactionType.TRANSFER, 0L);

        Mockito.when(applicationRestClient.callGetRestApi(ArgumentMatchers.anyString())).thenReturn(validResponseResult());

        ResponseResult responseResult = transactionService.processTransaction(userTransaction);

        Assert.assertNotNull(responseResult);
        Assert.assertTrue(responseResult.isValid());
    }


    private UserTransaction buildUserTransaction(long accountNumber, BigDecimal amount, TransactionType transactionType, long destinationAccount) {
        UserTransaction userTransaction = new UserTransaction();
        userTransaction.setAccountNumber(accountNumber);
        userTransaction.setAmount(amount);
        userTransaction.setTransactionType(transactionType);
        userTransaction.setToAccountNumber(destinationAccount);
        return userTransaction;
    }

    private TransactionResult validTransactionResult() {
        return new TransactionResult(new Account(), String.format("Success! Updated balance: [%.2f]", new BigDecimal(110.00)), true);
    }

    private ResponseResult validResponseResult() {

        return new ResponseResult(buildValidAccount());
    }

    private Account buildValidAccount() {
        Account account = new Account();
        account.setuId(1L);
        account.setBalance(new BigDecimal(100.00));
        account.setAccountNumber(1001L);
        return account;
    }



}
