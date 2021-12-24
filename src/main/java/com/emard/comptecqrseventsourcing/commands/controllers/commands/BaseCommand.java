package com.emard.comptecqrseventsourcing.commands.controllers.commands;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public abstract class BaseCommand<T> {

    @TargetAggregateIdentifier
    private T id;
}
