package com.transfermoney.model;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Class for storing the result of service calls.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseResult {

    private Account account;

    private String successMessage;

    private String errorMessage;

    private boolean valid;

    public ResponseResult(String errorMessage) {
        this.errorMessage = errorMessage;
        this.valid = false;
    }

    public ResponseResult(Account account) {
        this.account = account;
        this.valid = true;
    }

    public ResponseResult(Account account, String successMessage) {
        this.account = account;
        this.successMessage = successMessage;
        this.valid = true;
    }

    public Account getAccount() {
        return account;
    }

    public String getSuccessMessage() {
        return successMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public boolean isValid() {
        return valid;
    }
}
