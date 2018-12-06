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

@RunWith(MockitoJUnitRunner.class)
public class DepositTransactionTest {

    @InjectMocks
    private DepositTransaction transaction;

    @Mock
    private AccountRepository accountRepository;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void validTransaction() {
        TransactionResult transactionResult = transaction.processTransaction(
                buildTransactionContext(buildValidAccount(), new BigDecimal(100.00), null));

        Assert.assertNotNull(transactionResult);
        Assert.assertTrue(transactionResult.isSuccess());
        Assert.assertEquals(Constant.MSG_TRXN_SUCCESSFUL, transactionResult.getMessage());
    }

    private TransactionContext buildTransactionContext(Account account, BigDecimal amount, Account destinationAccount) {
        return new TransactionContext(account, amount, destinationAccount);
    }

    private Account buildValidAccount() {
        Account account = new Account();
        account.setuId(1L);
        account.setBalance(new BigDecimal(100.00));
        account.setAccountNumber(1001L);
        return account;
    }

}
