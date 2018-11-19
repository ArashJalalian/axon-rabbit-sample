package com.example.axon.rabbit.camunda.launcher;

import com.rabbitmq.client.Channel;
import org.axonframework.amqp.eventhandling.spring.SpringAMQPMessageSource;
import org.axonframework.commandhandling.SimpleCommandBus;
import org.axonframework.commandhandling.distributed.CommandBusConnector;
import org.axonframework.commandhandling.distributed.CommandRouter;
import org.axonframework.commandhandling.distributed.DistributedCommandBus;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.commandhandling.gateway.CommandGatewayFactory;
import org.axonframework.commandhandling.gateway.DefaultCommandGateway;
import org.axonframework.commandhandling.gateway.RetryScheduler;
import org.axonframework.config.EventProcessingConfiguration;
import org.axonframework.jgroups.commandhandling.JGroupsConnector;
import org.axonframework.serialization.Serializer;
import org.jgroups.JChannel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class CamundaLauncherConfiguration {

    @Autowired
    public void configure(EventProcessingConfiguration ehConfig, SpringAMQPMessageSource myMessageSource) {
        ehConfig.registerSubscribingEventProcessor("com.example.axon.rabbit.camunda.launcher", c -> myMessageSource);
    }

    @Bean
    public SpringAMQPMessageSource myMessageSource(Serializer serializer) {
        return new SpringAMQPMessageSource(serializer) {
            @Override
            @RabbitListener(queues = "my-event-queue")
            public void onMessage(Message message, Channel channel) {
                super.onMessage(message, channel);
            }
        };
    }





}
