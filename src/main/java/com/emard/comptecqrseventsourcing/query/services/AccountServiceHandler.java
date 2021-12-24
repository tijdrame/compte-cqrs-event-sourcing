package com.emard.comptecqrseventsourcing.query.services;

import java.time.LocalDate;
import java.util.List;

import com.emard.comptecqrseventsourcing.commonapi.enums.OperationType;
import com.emard.comptecqrseventsourcing.commonapi.events.AccountActivatedEvent;
import com.emard.comptecqrseventsourcing.commonapi.events.AccountCreatedEvent;
import com.emard.comptecqrseventsourcing.commonapi.events.AccountCreditedEvent;
import com.emard.comptecqrseventsourcing.commonapi.events.AccountDebitedEvent;
import com.emard.comptecqrseventsourcing.commonapi.queries.GetAccountByIdQuery;
import com.emard.comptecqrseventsourcing.commonapi.queries.GetAllAccountsQuery;
import com.emard.comptecqrseventsourcing.query.entities.Account;
import com.emard.comptecqrseventsourcing.query.entities.Operation;
import com.emard.comptecqrseventsourcing.query.repositories.AccountRepository;
import com.emard.comptecqrseventsourcing.query.repositories.OperationRepository;

import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class AccountServiceHandler {

    private final AccountRepository accountRepository;
    private final OperationRepository operationRepository;
    @EventHandler
    public void on(AccountCreatedEvent event) {
        log.info("***AccountCreatedEvent receiveid [{}]", event);
        accountRepository.save(new Account(
            event.getId(),
            event.getInitialBalance(),
            event.getStatus(),
            event.getCurrency(),
            null
        ));
    }

    @EventHandler
    public void on(AccountActivatedEvent event) {
        log.info("***AccountActivatedEvent receiveid [{}]", event);
        Account account = accountRepository.findById(event.getId()).get();
        account.setStatus(event.getStatus());
    }

    @EventHandler
    public void on(AccountDebitedEvent event) {
        log.info("***AccountDebitedEvent receiveid [{}]", event);
        Account account = accountRepository.findById(event.getId()).get();
        Operation operation = new  Operation();
        operation.setDateOp(LocalDate.now());//prendre la date depuis l'evenmt
        operation.setAccount(account);
        operation.setAmount(event.getAmount());
        operation.setType(OperationType.DEBIT);
        operationRepository.save(operation);
        account.setBalance(account.getBalance() - event.getAmount());
        account.getOperations().add(operation);
    }

    @EventHandler
    public void on(AccountCreditedEvent event) {
        log.info("***AccountCreditedEvent receiveid [{}]", event);
        Account account = accountRepository.findById(event.getId()).get();
        Operation operation = new  Operation();
        operation.setAccount(account);
        operation.setDateOp(LocalDate.now());//prendre la date depuis l'evenmt
        operation.setAmount(event.getAmount());
        operation.setType(OperationType.CREDIT);
        operationRepository.save(operation);
        account.setBalance(account.getBalance() + event.getAmount());
        account.getOperations().add(operation);
    }

    @QueryHandler
    public List<Account> on(GetAllAccountsQuery query) {
        return accountRepository.findAll();
    }

    @QueryHandler
    public Account on(GetAccountByIdQuery query) {
        return accountRepository.findById(query.getId()).get();
    }
}
