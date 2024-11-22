package com.room_scout.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    
    private static final String EMAIL_QUEUE = "emailQueue";
    private static final String EMAIL_EXCHANGE = "emailExchange";
    private static final String EMAIL_ROUTING_KEY = "emailRoutingKey";

    @Bean
    Queue emailQueue() {
        return new Queue(EMAIL_QUEUE, true);
    }

    @Bean
    DirectExchange emailExchange() {
        return new DirectExchange(EMAIL_EXCHANGE);
    }

    @Bean
    Binding emailBinding(Queue emailQueue, DirectExchange emailExchange) {
        return BindingBuilder.bind(emailQueue).to(emailExchange).with(EMAIL_ROUTING_KEY);
    }

    public String getExchange() {
        return EMAIL_EXCHANGE;
    }

    public String getRoutingKey() {
        return EMAIL_ROUTING_KEY;
    }
}
