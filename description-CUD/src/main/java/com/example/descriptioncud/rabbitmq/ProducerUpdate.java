package com.example.descriptioncud.rabbitmq;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.descriptioncud.model.DescriptionDTO;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class ProducerUpdate {

    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;

    public ProducerUpdate(RabbitTemplate rabbitTemplate, ObjectMapper objectMapper) {
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = objectMapper;
    }

    public void updateDescription(String title, DescriptionDTO dto){
        try {
            String message = objectMapper.writeValueAsString(dto);
            rabbitTemplate.convertAndSend(RabbitQueueConfig.EXCHANGE3_NAME, RabbitQueueConfig.ROUTING_KEY3, message);
        } catch (JsonProcessingException e) {
            System.err.println("Error serializing message: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
