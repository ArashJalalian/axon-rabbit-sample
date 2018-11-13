package com.example.axon.rabbit.camunda.launcher;

import com.example.axon.rabbit.common.domain.Task1CompletedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class Task1CompletedListener {

    @EventHandler
    public void handle(Task1CompletedEvent event) {
        // fire up a Communda message.
        log.info("task1 completed: {}", event);
    }
}
