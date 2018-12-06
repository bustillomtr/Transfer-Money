package com.transfermoney.service.transaction;

import com.transfermoney.model.Account;
import com.transfermoney.util.Constant;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

/**
 * Class for processing transfer transaction
 */
@Service
public class TransferTransaction extends AbstractTransaction {

    @Override
    public TransactionResult processTransaction(TransactionContext context) {
        Account account = context.getAccount();
        Account destinationAccount = context.getDestinationAccount();
        BigDecimal amount = context.getAmount();

        if (destinationAccount == null) {
            return new TransactionResult(Constant.ERR_MSG_DEST_ACCOUNT_NOT_FOUND, false);
        }

        if (account.getBalance().doubleValue() >= amount.doubleValue()) {
            account.setBalance(account.getBalance().subtract(amount));
            destinationAccount.setBalance(destinationAccount.getBalance().add(amount));
            // save them
            List<Account> updatedAccounts = accountRepository.saveAll(Arrays.asList(account, destinationAccount));

            return new TransactionResult(updatedAccounts.get(0), Constant.MSG_TRXN_SUCCESSFUL, true);

        } else {
            return new TransactionResult(Constant.ERR_MSG_INSUFFICIENT_FUND, false);
        }
    }

    @Override
    public TransactionType getTransactionType() {
        return TransactionType.TRANSFER;
    }
}
