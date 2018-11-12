package com.example.axon.rabbit.ms0;

import com.example.axon.rabbit.common.domain.MyAggregateCreatedEvent;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.axonframework.eventhandling.saga.EndSaga;
import org.axonframework.eventhandling.saga.SagaEventHandler;
import org.axonframework.eventhandling.saga.StartSaga;
import org.axonframework.spring.stereotype.Saga;

import java.util.UUID;

@Getter
@NoArgsConstructor
@Saga
public class MySaga {
    private UUID id;

    @StartSaga
    @EndSaga
    @SagaEventHandler(associationProperty = "id")
    public void on(MyAggregateCreatedEvent event) {
        this.id = event.getId();
    }
}
