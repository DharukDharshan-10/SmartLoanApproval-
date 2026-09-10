package com.loan.approval;

public class InvalidLoanDataException extends Exception {

    public InvalidLoanDataException(String message) {
        super(message);
    }
}
