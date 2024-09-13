package uk.co.sainsburys.clients.service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.micrometer.observation.ObservationRegistry;

@Configuration
public class ClientsServiceConfig {
    @Bean
    public ClientsService clientsService(ObservationRegistry observationRegistry) {
        return new ObservedClientsServiceImpl(new ClientsServiceImpl(), observationRegistry);
    }
}
