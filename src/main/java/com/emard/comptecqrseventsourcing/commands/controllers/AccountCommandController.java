package com.emard.comptecqrseventsourcing.commands.controllers;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

import com.emard.comptecqrseventsourcing.commands.controllers.commands.CreateAccountCommand;
import com.emard.comptecqrseventsourcing.commands.controllers.commands.CreditAccountCommand;
import com.emard.comptecqrseventsourcing.commands.controllers.commands.DebitAccountCommand;
import com.emard.comptecqrseventsourcing.commonapi.dtos.CreateAccountRequestDTO;
import com.emard.comptecqrseventsourcing.commonapi.dtos.CreditAccountRequestDTO;
import com.emard.comptecqrseventsourcing.commonapi.dtos.DebiAccountRequestDTO;

import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping(path = "/commands/account")
@AllArgsConstructor
public class AccountCommandController {

    private CommandGateway commandGateway;
    private EventStore eventStore;

    @PostMapping("/create")
    public CompletableFuture<String> createAccount(@RequestBody CreateAccountRequestDTO request) {
        CompletableFuture<String> commandResponse = commandGateway.send(new CreateAccountCommand(
                UUID.randomUUID().toString(), request.getInitialBalance(), request.getCurrency()));
        return commandResponse;
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> exceptonHandler(Exception exception) {
        ResponseEntity<String> entity = new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        return entity;
    }

    @GetMapping("/eventStore/{accountId}")
    private Stream eventStore(@PathVariable String accountId){
        return eventStore.readEvents(accountId).asStream();
    }

    @PutMapping("/credit")
    public CompletableFuture<String> debitAccount(@RequestBody CreditAccountRequestDTO request) {
        CompletableFuture<String> commandResponse = commandGateway.send(new CreditAccountCommand(
                request.getAccountId(), request.getAmount(), request.getCurrency()));
        return commandResponse;
    }

    @PutMapping("/debit")
    public CompletableFuture<String> creditAccount(@RequestBody DebiAccountRequestDTO request) {
        CompletableFuture<String> commandResponse = commandGateway.send(new DebitAccountCommand(
                request.getAccountId(), request.getAmount(), request.getCurrency()));
        return commandResponse;
    }
}
