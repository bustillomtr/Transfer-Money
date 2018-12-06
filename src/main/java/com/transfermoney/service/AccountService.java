package com.transfermoney.service;

import com.transfermoney.model.Account;
import com.transfermoney.model.ResponseResult;
import com.transfermoney.repository.AccountRepository;
import com.transfermoney.util.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service class for the account.
 */
@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    /**
     * Retrieves the Account by accountNumber
     *
     * @param accountNumber
     * @return
     */
    public ResponseResult getAccount(long accountNumber) {
        Account account = accountRepository.findByAccountNumber(accountNumber);

        return account == null ?
                new ResponseResult(Constant.ERR_MSG_ACCOUNT_NOT_FOUND) :
                new ResponseResult(account);
    }

}
