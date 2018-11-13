package com.example.axon.rabbit.camunda.launcher;

import com.example.axon.rabbit.common.domain.RunTask1Command;
import lombok.RequiredArgsConstructor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class Task1 implements JavaDelegate {

    private final CommandGateway commandGateway;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        String message = execution.getVariable("message").toString();
        UUID task1Id = commandGateway.sendAndWait(new RunTask1Command(UUID.randomUUID(), message));
        execution.setVariable("task1Id", task1Id.toString());
    }
}
