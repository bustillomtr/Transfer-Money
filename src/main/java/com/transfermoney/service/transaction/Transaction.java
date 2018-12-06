package com.transfermoney.service.transaction;

/**
 * Interface for transaction.
 */
public interface Transaction {

    /**
     * To process the transaction.
     *
     * @param context   this will contain the context of the transaction
     * @return
     */
    TransactionResult process(TransactionContext context);

    /**
     * To get the type of the transaction.
     *
     * @return
     */
    TransactionType getTransactionType();

}
