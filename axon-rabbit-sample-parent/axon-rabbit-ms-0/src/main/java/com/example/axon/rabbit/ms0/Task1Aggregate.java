package com.example.axon.rabbit.ms0;

import com.example.axon.rabbit.common.domain.RunTask1Command;
import com.example.axon.rabbit.common.domain.Task1CompletedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.commandhandling.model.AggregateLifecycle;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.spring.stereotype.Aggregate;

import java.util.UUID;

@Aggregate
public class Task1Aggregate {

    @AggregateIdentifier
    private UUID id;

    private String message;

    @CommandHandler
    public Task1Aggregate(RunTask1Command command) {
        AggregateLifecycle.apply(new Task1CompletedEvent(command.getId(), command.getMessage()));
    }

    @EventHandler
    public void on(Task1CompletedEvent event) {
        this.id = event.getId();
        this.message = event.getMessage();
    }

}
