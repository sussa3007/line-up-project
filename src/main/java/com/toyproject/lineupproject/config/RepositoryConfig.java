package com.toyproject.lineupproject.config;

import com.toyproject.lineupproject.repository.EventRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RepositoryConfig {

    @Bean
    public EventRepository eventRepository() {
        return new EventRepository() {

        };
    }
}
