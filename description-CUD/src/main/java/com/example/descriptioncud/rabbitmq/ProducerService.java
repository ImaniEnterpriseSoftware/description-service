package com.example.descriptioncud.rabbitmq;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.descriptioncud.model.DescriptionDTO;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class ProducerService {

    private static final String EXCHANGE_NAME = "descr-exchange";
    private static final String ROUTING_KEY = "routing-key-descr";
    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;

    public ProducerService(RabbitTemplate rabbitTemplate, ObjectMapper objectMapper) {
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = objectMapper;
    }

    public void sendDescription(DescriptionDTO dto){
        try {
            String message = objectMapper.writeValueAsString(dto);
            rabbitTemplate.convertAndSend(EXCHANGE_NAME, ROUTING_KEY, message);
        } catch (JsonProcessingException e) {
            System.err.println("Error serializing message: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
