package com.transfermoney.service.transaction;

import com.transfermoney.model.Account;
import com.transfermoney.util.Constant;
import org.springframework.stereotype.Service;

/**
 * Class for processing deposit transaction
 */
@Service
public class DepositTransaction extends AbstractTransaction {

    @Override
    public TransactionResult processTransaction(TransactionContext context) {
        Account account = context.getAccount();

        account.setBalance(account.getBalance().add(context.getAmount()));
        Account updatedAccount = accountRepository.save(account);

        return new TransactionResult(updatedAccount, Constant.MSG_TRXN_SUCCESSFUL, true);
    }

    @Override
    public TransactionType getTransactionType() {
        return TransactionType.DEPOSIT;
    }
}
