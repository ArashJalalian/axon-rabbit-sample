package com.exampleaxon.rabbit.orchestrator;

import com.netflix.appinfo.ApplicationInfoManager;
import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.SimpleCommandBus;
import org.axonframework.commandhandling.distributed.*;
import org.axonframework.serialization.Serializer;
import org.axonframework.springcloud.commandhandling.SpringCloudCommandRouter;
import org.axonframework.springcloud.commandhandling.SpringHttpCommandBusConnector;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.DefaultServiceInstance;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.simple.SimpleDiscoveryProperties;
import org.springframework.cloud.client.serviceregistry.Registration;
import org.springframework.cloud.netflix.eureka.EurekaDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Configuration
public class AxonDistributedConfiguration {

    @Bean
    public RoutingStrategy routingStrategy() {
        // uses the @TargetAggregateIdentifier annotation for routing.
        return new AnnotationRoutingStrategy();
    }

    @Bean
    public CommandBus localCommandBus() {
        return new SimpleCommandBus();
    }


    @Bean
    public Registration localRegistration() {
        final UUID uuid = UUID.randomUUID();
        return new Registration() {
            @Override
            public String getServiceId() {
                return uuid.toString();
            }

            @Override
            public String getHost() {
                return "localhost";
            }

            @Override
            public int getPort() {
                return 8099;
            }

            @Override
            public boolean isSecure() {
                return false;
            }

            @Override
            public URI getUri() {
                try {
                    return new URI("http://localhost:8099");
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public Map<String, String> getMetadata() {
                HashMap<String, String> map = new HashMap<>();
                map.put("management.port", "8099");
                map.put("jmx.port", "62370");
                return map;
            }
        };
    }

//    @Bean
//    public CommandRouter springCloudCommandRouter(DiscoveryClient discoveryClient,
//                                                  RoutingStrategy routingStrategy,
//                                                  Registration localRegistration,
//                                                  ApplicationInfoManager applicationInfoManager) {
//
//        applicationInfoManager.registerAppMetadata(localRegistration.getMetadata());
//        return new SpringCloudCommandRouter(discoveryClient, localRegistration, routingStrategy);
//    }

    @Bean
    public RestOperations restOperations() {
        return new RestTemplate();
    }

    @Bean
    public CommandBusConnector springHttpCommandBusConnector(@Qualifier("localCommandBus") CommandBus localCommandBus,
                                                             RestOperations restOperations,
                                                             Serializer serializer) {
        return new SpringHttpCommandBusConnector(localCommandBus, restOperations, serializer);
    }

//    @Primary
//    @Bean
//    public DistributedCommandBus springCloudDistributedCommandBus(CommandRouter springCloudCommandRouter,
//                                                                  CommandBusConnector springHttpCommandBusConnector) {
//        return new DistributedCommandBus(springCloudCommandRouter, springHttpCommandBusConnector);
//    }

}
