package com.example.axon.rabbit.common.domain;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class MyAggregateCreatedEvent {
    private UUID id;
    private String message;
}
