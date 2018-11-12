package com.example.axon.rabbit.ms0;

import com.example.axon.rabbit.common.domain.CreateMyAggregateCommand;
import lombok.RequiredArgsConstructor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("v1/ms0")
@RequiredArgsConstructor
@Transactional
public class MainController {

    private final CommandGateway commandGateway;

    @PostMapping("create-aggregate")
    public ResponseEntity<String> createAggregate(@RequestBody CreateMyAggregateCommand createMyAggregateCommand) {
        UUID uuid = commandGateway.sendAndWait(createMyAggregateCommand);
        return ResponseEntity.ok(uuid.toString());
    }
}
