package com.example.axon.rabbit.ms2;

import com.example.axon.rabbit.common.domain.MyAggregateCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class MyEventListener {

    private final MyAggregateViewRepository myAggregateViewRepository;

    @EventHandler
    public void handle(MyAggregateCreatedEvent event) {
        myAggregateViewRepository.saveAndFlush(new MyAggregateView(event.getId(), event.getMessage()));
        Optional<MyAggregateView> byId = myAggregateViewRepository.findById(event.getId());
        if (!byId.isPresent()) {
            throw new RuntimeException("unable to update database view.");
        }
        log.info("view updated for id: {}", byId.get().getId());
    }
}
