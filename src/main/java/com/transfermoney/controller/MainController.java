package com.transfermoney.controller;

import com.transfermoney.model.ResponseResult;
import com.transfermoney.model.UserTransaction;
import com.transfermoney.service.AccountService;
import com.transfermoney.service.TransactionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Application's main controller.
 */
@RestController
@Api(value = "Operations pertaining to Account")
public class MainController {

    @Autowired
    private AccountService accountService;
    @Autowired
    private TransactionService transactionService;

    /**
     * Entry point for getting the current balance of an account number.
     *
     * @param accountNumber user's valid account number
     * @return
     */
    @ApiOperation(value = "Get current balance of the account.", response = ResponseEntity.class)
    @GetMapping("/getbalance/{accountNumber}")
    public ResponseEntity<Object> processGetRequest(@PathVariable("accountNumber") long accountNumber) {
        ResponseResult result = accountService.getAccount(accountNumber);

        if (result.isValid()) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Entry point for processing a transaction request.
     *
     * @param userTransaction
     * @return
     */
    @ApiOperation(value = "To perform account's transaction.", response = ResponseEntity.class)
    @PutMapping("/transaction")
    public ResponseEntity<Object> processPutRequest(@RequestBody UserTransaction userTransaction) {
        ResponseResult result = transactionService.processTransaction(userTransaction);

        if (result.isValid()) {
            return new ResponseEntity<>(result, HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
    }

}
