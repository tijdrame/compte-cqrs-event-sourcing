package com.emard.comptecqrseventsourcing.commonapi.events;

import com.emard.comptecqrseventsourcing.commonapi.enums.AccountStatus;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class AccountActivatedEvent extends BaseEvent<String>{

    private AccountStatus status;
    public AccountActivatedEvent(String id, AccountStatus status) {
        super(id);
        this.status = status;
    }
    
}
