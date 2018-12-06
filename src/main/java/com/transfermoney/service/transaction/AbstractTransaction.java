package com.transfermoney.service.transaction;

import com.transfermoney.repository.AccountRepository;
import com.transfermoney.util.Constant;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Abstract class for Transaction
 */
public abstract class AbstractTransaction implements Transaction {

    @Autowired
    protected AccountRepository accountRepository;

    /**
     * This method will ensure that the TransactionContext meet the required fields.
     *
     * @param context   the context of the Transaction
     * @return
     */
    @Override
    public TransactionResult process(TransactionContext context) {
        if (context == null) {
            return new TransactionResult(Constant.ERR_MSG_INVALID_REQUEST, false);
        }
        if (context.getAccount() == null) {
            return new TransactionResult(Constant.ERR_MSG_ACCOUNT_NOT_FOUND, false);
        }
        if (context.getAmount().doubleValue() == 0) {
            return new TransactionResult(Constant.ERR_MSG_INVALID_AMOUNT, false);
        }

        return processTransaction(context);
    }

    /**
     * To process the transaction.
     *
     * @param context
     * @return
     */
    public abstract TransactionResult processTransaction(TransactionContext context);

}
