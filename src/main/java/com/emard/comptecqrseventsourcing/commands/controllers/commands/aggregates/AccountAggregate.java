package com.emard.comptecqrseventsourcing.commands.controllers.commands.aggregates;

import com.emard.comptecqrseventsourcing.commands.controllers.commands.CreateAccountCommand;
import com.emard.comptecqrseventsourcing.commands.controllers.commands.CreditAccountCommand;
import com.emard.comptecqrseventsourcing.commands.controllers.commands.DebitAccountCommand;
import com.emard.comptecqrseventsourcing.commonapi.enums.AccountStatus;
import com.emard.comptecqrseventsourcing.commonapi.events.AccountActivatedEvent;
import com.emard.comptecqrseventsourcing.commonapi.events.AccountCreatedEvent;
import com.emard.comptecqrseventsourcing.commonapi.events.AccountCreditedEvent;
import com.emard.comptecqrseventsourcing.commonapi.events.AccountDebitedEvent;
import com.emard.comptecqrseventsourcing.commonapi.exception.AmountNegativeException;
import com.emard.comptecqrseventsourcing.commonapi.exception.BalanceNotSufficientException;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import lombok.NoArgsConstructor;

@Aggregate
@NoArgsConstructor//required by Axon
public class AccountAggregate {

    @AggregateIdentifier
    private String accountId;
    private Double balance;
    private String currency;
    private AccountStatus status;

    @CommandHandler
    public AccountAggregate(CreateAccountCommand create) {
        if(create.getInitialBalance() < 0) throw new RuntimeException("Impossible de creer un compte avec un solde négatif");
        // & so if all is OK
        AggregateLifecycle.apply(new AccountCreatedEvent(create.getId(), create.getInitialBalance(), create.getCurrency(), AccountStatus.CREATED));
    }

    @EventSourcingHandler
    public void on(AccountCreatedEvent event) {
        this.accountId = event.getId();
        this.balance = event.getInitialBalance();
        this.currency = event.getCurrency();
        this.status = AccountStatus.CREATED;
        AggregateLifecycle.apply(new AccountActivatedEvent(event.getId(), AccountStatus.ACTIVATED));
    }

    public void on(AccountActivatedEvent event) {
        this.status = event.getStatus();
    }

    @CommandHandler
    public void handle(CreditAccountCommand command){
        if(command.getAmount()<0) throw new AmountNegativeException("Amount should not be negative");
        AggregateLifecycle.apply(new AccountCreditedEvent(command.getId(), command.getAmount(), command.getCurrency()));
    }

    @EventSourcingHandler
    public void on(AccountCreditedEvent event) {
        this.balance += event.getAmount();
    }

    @CommandHandler
    public void handle(DebitAccountCommand command){
        if(command.getAmount()<0) throw new AmountNegativeException("Amount should not be negative");
        if(this.balance < command.getAmount()) throw new BalanceNotSufficientException("Balance not sufficient Exception ="+this.balance);
        AggregateLifecycle.apply(new AccountDebitedEvent(command.getId(), command.getAmount(), command.getCurrency()));
    }

    @EventSourcingHandler
    public void on(AccountDebitedEvent event) {
        this.balance -= event.getAmount();
    }

}
