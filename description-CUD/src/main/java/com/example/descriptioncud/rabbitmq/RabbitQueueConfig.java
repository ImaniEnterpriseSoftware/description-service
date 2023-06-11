package com.example.descriptioncud.rabbitmq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitQueueConfig {
    static final String QUEUE1_NAME = "description";
    static final String QUEUE2_NAME = "description-delete";
    static final String QUEUE3_NAME = "description-update";

    static final String EXCHANGE1_NAME = "descr-exchange";
    static final String EXCHANGE2_NAME = "descr-delete";
    static final String EXCHANGE3_NAME = "descr-update";

    static final String ROUTING_KEY1 = "routing-key-descr";
    static final String ROUTING_KEY2 = "routing-key-descr-delete";
    static final String ROUTING_KEY3 = "routing-key-descr-update";

    @Bean
    Queue queue1() {
        return new Queue(QUEUE1_NAME, true);
    }

    @Bean
    Queue queue2() {
        return new Queue(QUEUE2_NAME, true);
    }

    @Bean
    Queue queue3() {
        return new Queue(QUEUE3_NAME, true);
    }

    @Bean
    TopicExchange exchange1() {
        return new TopicExchange(EXCHANGE1_NAME);
    }

    @Bean
    TopicExchange exchange2() {
        return new TopicExchange(EXCHANGE2_NAME);
    }

    @Bean
    TopicExchange exchange3() {
        return new TopicExchange(EXCHANGE3_NAME);
    }

    @Bean
    Binding binding1(Queue queue1, TopicExchange exchange1) {
        return BindingBuilder.bind(queue1).to(exchange1).with(ROUTING_KEY1);
    }

    @Bean
    Binding binding2(Queue queue2, TopicExchange exchange2) {
        return BindingBuilder.bind(queue2).to(exchange2).with(ROUTING_KEY2);
    }

    @Bean
    Binding binding3(Queue queue3, TopicExchange exchange3) {
        return BindingBuilder.bind(queue3).to(exchange3).with(ROUTING_KEY3);
    }

    // ...
}

