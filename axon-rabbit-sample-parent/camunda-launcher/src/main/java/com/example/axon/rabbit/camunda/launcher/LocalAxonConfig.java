package com.example.axon.rabbit.camunda.launcher;

import org.axonframework.commandhandling.SimpleCommandBus;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.commandhandling.gateway.DefaultCommandGateway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LocalAxonConfig {
    @Bean
    public CommandGateway localCommandGateway(SimpleCommandBus localCommandBus) {
        return new DefaultCommandGateway(localCommandBus);
    }

    @Bean
    public SimpleCommandBus localCommandBus() {
        return new SimpleCommandBus();
    }
}
