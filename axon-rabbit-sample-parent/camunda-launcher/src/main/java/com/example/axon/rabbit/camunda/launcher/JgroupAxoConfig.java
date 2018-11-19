package com.example.axon.rabbit.camunda.launcher;

import org.axonframework.commandhandling.SimpleCommandBus;
import org.axonframework.commandhandling.distributed.DistributedCommandBus;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.commandhandling.gateway.DefaultCommandGateway;
import org.axonframework.jgroups.commandhandling.JGroupsConnector;
import org.axonframework.serialization.Serializer;
import org.jgroups.JChannel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

//@Configuration
public class JgroupAxoConfig {

    @Bean
    public CommandGateway distributedCommandGateway(DistributedCommandBus distributedCommandBus) {
        return new DefaultCommandGateway(distributedCommandBus);
    }

    @Primary
    @Bean
    public DistributedCommandBus distributedCommandBus(JGroupsConnector jGroupsConnector) {
        return new DistributedCommandBus(jGroupsConnector, jGroupsConnector);
    }
    @Bean
    public JGroupsConnector jGroupsConnector(JChannel channel, SimpleCommandBus localCommandBus, Serializer serializer) {
        return new JGroupsConnector(localCommandBus, channel, "distributedCommandBus", serializer);
    }

    @Bean
    public JChannel channel() {
        try {
            return new JChannel();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
