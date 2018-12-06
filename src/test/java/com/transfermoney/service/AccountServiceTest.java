package com.transfermoney.service;

import com.transfermoney.model.Account;
import com.transfermoney.model.ResponseResult;
import com.transfermoney.repository.AccountRepository;
import com.transfermoney.util.Constant;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class AccountServiceTest {

    @InjectMocks
    private AccountService accountService;

    @Mock
    private AccountRepository accountRepository;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getExistingAccount() {
        Mockito.when(accountRepository.findByAccountNumber(ArgumentMatchers.anyLong())).thenReturn(new Account());

        ResponseResult responseResult = accountService.getAccount(1001L);

        Assert.assertNotNull(responseResult);
        Assert.assertNotNull(responseResult.getAccount());
    }

    @Test
    public void getNonExistentAccount() {
        Mockito.when(accountRepository.findByAccountNumber(ArgumentMatchers.anyLong())).thenReturn(null);

        ResponseResult responseResult = accountService.getAccount(9999L);

        Assert.assertNotNull(responseResult);
        Assert.assertNull(responseResult.getAccount());
        Assert.assertEquals(Constant.ERR_MSG_ACCOUNT_NOT_FOUND, responseResult.getErrorMessage());
    }

}
