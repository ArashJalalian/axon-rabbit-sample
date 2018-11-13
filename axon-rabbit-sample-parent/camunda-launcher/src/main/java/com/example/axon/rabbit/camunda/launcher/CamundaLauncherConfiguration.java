package com.example.axon.rabbit.camunda.launcher;

import com.rabbitmq.client.Channel;
import org.axonframework.amqp.eventhandling.spring.SpringAMQPMessageSource;
import org.axonframework.config.EventProcessingConfiguration;
import org.axonframework.serialization.Serializer;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
