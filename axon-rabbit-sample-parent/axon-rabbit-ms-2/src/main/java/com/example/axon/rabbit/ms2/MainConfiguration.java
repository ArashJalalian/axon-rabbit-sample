package com.example.axon.rabbit.ms2;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.amqp.eventhandling.spring.SpringAMQPMessageSource;
import org.axonframework.config.EventProcessingConfiguration;
import org.axonframework.eventsourcing.eventstore.EventStorageEngine;
import org.axonframework.eventsourcing.eventstore.inmemory.InMemoryEventStorageEngine;
import org.axonframework.serialization.Serializer;
import org.axonframework.serialization.json.JacksonSerializer;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;


@Configuration
@Slf4j
public class MainConfiguration {
    @Primary
    @Bean
    public Serializer serializer() {
        return new JacksonSerializer();
    }

    @Qualifier("messageSerializer")
    @Bean
    public Serializer messageSerializer() {
        return new JacksonSerializer();
    }



    @Bean
    public SpringAMQPMessageSource myMessageSource(Serializer serializer) {
        return new SpringAMQPMessageSource(serializer) {
            @Override
            @RabbitListener(queues = "my-event-queue")
            public void onMessage(Message message, Channel channel) {
                log.info("message received {}", message.toString());
                super.onMessage(message, channel);
            }
        };
    }



    @Bean
    public EventStorageEngine eventStorageEngine() {
        // somewhere in configuration
        // When a EventStorageEngine is provided, the EmbeddedEventStore is configured as the EventStore.
        // And if JPA configuration is detected JpaEventStorageEngine is configured as EventStorageEngine.
        return new InMemoryEventStorageEngine();
    }

    @Autowired
    public void configure(EventProcessingConfiguration ehConfig, SpringAMQPMessageSource myMessageSource) {
        ehConfig.registerSubscribingEventProcessor("com.example.axon.rabbit.ms2", c -> myMessageSource);
    }
}
