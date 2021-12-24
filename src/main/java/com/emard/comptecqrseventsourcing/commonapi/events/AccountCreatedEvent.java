package com.emard.comptecqrseventsourcing.commonapi.events;

import com.emard.comptecqrseventsourcing.commonapi.enums.AccountStatus;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class AccountCreatedEvent extends BaseEvent<String>{

    private Double initialBalance;
    private String currency;
    private AccountStatus status;
    public AccountCreatedEvent(String id, Double initialBalance, String currency, AccountStatus status) {
        super(id);
        this.initialBalance = initialBalance;
        this.currency = currency;
        this.status = status;
    }
    
}
