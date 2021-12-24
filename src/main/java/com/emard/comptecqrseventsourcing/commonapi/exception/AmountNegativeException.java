package com.emard.comptecqrseventsourcing.commonapi.exception;

public class AmountNegativeException extends RuntimeException{
    public AmountNegativeException(String msg) {
        super(msg);
    }
}
