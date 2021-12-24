package com.emard.comptecqrseventsourcing.commonapi.exception;

public class BalanceNotSufficientException extends RuntimeException {
 
    public BalanceNotSufficientException(String msg){
        super(msg);
    }
}
