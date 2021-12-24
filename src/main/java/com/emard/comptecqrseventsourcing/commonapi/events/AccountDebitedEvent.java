package com.emard.comptecqrseventsourcing.commonapi.events;


import lombok.Getter;
import lombok.ToString;

/**
 * AccountCreditedEvent
 */
@Getter
@ToString
public class AccountDebitedEvent extends BaseEvent<String>{
    private Double amount;
    private String currency;
    public AccountDebitedEvent(String id, Double amount, String currency) {
        super(id);
        this.amount = amount;
        this.currency = currency;
    }

    
}