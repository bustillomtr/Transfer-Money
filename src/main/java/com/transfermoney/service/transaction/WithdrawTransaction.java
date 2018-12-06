package com.transfermoney.service.transaction;

import com.transfermoney.model.Account;
import com.transfermoney.util.Constant;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * Class for processing withdrawal transaction
 */
@Service
public class WithdrawTransaction extends AbstractTransaction {

    @Override
    public TransactionResult processTransaction(TransactionContext context) {
        Account account = context.getAccount();

        BigDecimal currentBalance = account.getBalance();
        if (context.getAmount().doubleValue() > currentBalance.doubleValue()) {
            return new TransactionResult(Constant.ERR_MSG_INSUFFICIENT_FUND, false);
        }

        account.setBalance(currentBalance.subtract(context.getAmount()));
        Account updatedAccount = accountRepository.save(account);

        return new TransactionResult(updatedAccount, Constant.MSG_TRXN_SUCCESSFUL, true);
    }

    @Override
    public TransactionType getTransactionType() {
        return TransactionType.WITHDRAW;
    }
}
