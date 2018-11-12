package com.example.axon.rabbit.ms2;


import com.example.axon.rabbit.common.domain.MyAggregateCreatedEvent;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.saga.EndSaga;
import org.axonframework.eventhandling.saga.SagaEventHandler;
import org.axonframework.eventhandling.saga.SagaLifecycle;
import org.axonframework.eventhandling.saga.StartSaga;
import org.axonframework.spring.stereotype.Saga;

import java.util.UUID;

@Getter
@NoArgsConstructor
@Saga(configurationBean = "mySagaSagaConfiguration")
@Slf4j
public class MySaga {
    private UUID id;

    @StartSaga
    @SagaEventHandler(associationProperty = "id")
    public void on(MyAggregateCreatedEvent event) {
        this.id = event.getId();
        log.info("event received in saga {}", event);
        SagaLifecycle.end();
    }
}
