package com.example.descriptioncud.rabbitmq;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.descriptioncud.model.DescriptionDTO;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class ProducerDelete {

    private static final String EXCHANGE_NAME = "descr-delete";
    private static final String ROUTING_KEY = "routing-key-descr-delete";
    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;

    public ProducerDelete(RabbitTemplate rabbitTemplate, ObjectMapper objectMapper) {
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = objectMapper;
    }

    public void deleteDescription(String title, DescriptionDTO dto){
        try {
            String message = objectMapper.writeValueAsString(dto);
            rabbitTemplate.convertAndSend(EXCHANGE_NAME, ROUTING_KEY, message);
        } catch (JsonProcessingException e) {
            System.err.println("Error serializing message: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
