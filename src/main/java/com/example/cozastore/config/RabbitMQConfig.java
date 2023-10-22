package com.example.cozastore.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.core.Queue;


@Configuration
public class RabbitMQConfig {
    @Bean
    public Queue queue(){
        return new Queue("storage22");
    }

    @Bean
    public TopicExchange exchange(){
        return new TopicExchange("exstorage22");
    }

    @Bean
    public Binding binding(){
        return BindingBuilder
                .bind(queue())
                .to(exchange())
                .with("/routeStorage22");
    }
}
