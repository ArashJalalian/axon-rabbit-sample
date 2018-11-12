package com.example.axon.rabbit.ms2;

import com.example.axon.rabbit.common.domain.MyAggregateCreatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MyEventListener {

    @EventHandler
    public void handle(MyAggregateCreatedEvent event) {
        log.info("event received in handler.", event.getId());
    }
}
