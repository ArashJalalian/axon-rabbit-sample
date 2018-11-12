package com.example.axon.rabbit.common.domain;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.model.AggregateIdentifier;

import java.util.UUID;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreateMyAggregateCommand {

    @AggregateIdentifier
    private UUID id;

    private String message;
}
