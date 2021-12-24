package com.emard.comptecqrseventsourcing.query.controllers;

import java.util.List;

import com.emard.comptecqrseventsourcing.commonapi.queries.GetAccountByIdQuery;
import com.emard.comptecqrseventsourcing.commonapi.queries.GetAllAccountsQuery;
import com.emard.comptecqrseventsourcing.query.entities.Account;

import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/query/accounts")
@AllArgsConstructor
public class AccountQueryController {

    private final QueryGateway queryGateway;

    @GetMapping("/allAcounts")
    public List<Account> accountList() {
        return queryGateway.query(new GetAllAccountsQuery(), ResponseTypes.multipleInstancesOf(Account.class)).join();
    }

    @GetMapping("/getAcount/{id}")
    public Account getAcount(@PathVariable String id) {
        return queryGateway.query(new GetAccountByIdQuery(id), Account.class).join();
    }
}
