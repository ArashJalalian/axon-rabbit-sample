package com.example.axon.rabbit.ms0;

import org.axonframework.eventsourcing.eventstore.EventStorageEngine;
import org.axonframework.eventsourcing.eventstore.inmemory.InMemoryEventStorageEngine;
import org.axonframework.serialization.Serializer;
import org.axonframework.serialization.json.JacksonSerializer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class MainConfiguration {

    @Primary // Marking it primary means this is the one to use, if no specific Qualifier is requested
    @Bean
    public Serializer serializer() {
        return new JacksonSerializer();
    }

    @Qualifier("messageSerializer")
    @Bean
    public Serializer messageSerializer() {
        return new JacksonSerializer();
    }

    @Qualifier("eventSerializer")
    @Bean
    public Serializer eventSerializer() {
        return new JacksonSerializer();
    }



    @Bean
    public EventStorageEngine eventStorageEngine() {
        // somewhere in configuration
        // When a EventStorageEngine is provided, the EmbeddedEventStore is configured as the EventStore.
        // And if JPA configuration is detected JpaEventStorageEngine is configured as EventStorageEngine.
        return new InMemoryEventStorageEngine();
    }

}
