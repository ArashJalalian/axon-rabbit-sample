package com.example.axon.rabbit.ms0;

import com.example.axon.rabbit.common.domain.CreateMyAggregateCommand;
import com.example.axon.rabbit.common.domain.MyAggregateCreatedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.commandhandling.model.AggregateLifecycle;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.spring.stereotype.Aggregate;

import java.util.UUID;

@Aggregate
public class MyAggregate {

    @AggregateIdentifier
    private UUID id;

    private String message;

    @CommandHandler
    public MyAggregate(CreateMyAggregateCommand createMyAggregateCommand) {
        AggregateLifecycle.apply(new MyAggregateCreatedEvent(UUID.randomUUID(), createMyAggregateCommand.getMessage()));
    }

    @EventHandler
    public void on(MyAggregateCreatedEvent event) {
        this.id = event.getId();
        this.message = event.getMessage();
    }
}
