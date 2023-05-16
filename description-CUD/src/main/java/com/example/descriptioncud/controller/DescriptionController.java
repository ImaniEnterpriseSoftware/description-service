package com.example.descriptioncud.controller;

import com.example.descriptioncud.model.Description;
import com.example.descriptioncud.model.DescriptionDTO;
import com.example.descriptioncud.rabbitmq.ProducerDelete;
import com.example.descriptioncud.rabbitmq.ProducerService;
import com.example.descriptioncud.rabbitmq.ProducerUpdate;
import com.example.descriptioncud.service.DescriptionService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/descriptions")
public class DescriptionController {

    private final DescriptionService descriptionService;
    private final ProducerService producerService;
    private final ProducerUpdate producerUpdate;
    private final ProducerDelete producerDelete;
    private final ModelMapper modelMapper;

    public DescriptionController(DescriptionService descriptionService, ProducerService producerService, ModelMapper modelMapper, ProducerUpdate producerUpdate, ProducerDelete producerDelete) {
        this.descriptionService = descriptionService;
        this.producerService = producerService;
        this.modelMapper = modelMapper;
        this.producerUpdate = producerUpdate;
        this.producerDelete = producerDelete;
    }

    @GetMapping
    public List<Description> getAll(){
        return descriptionService.getAllDescriptions();
    }

    @PostMapping
    public ResponseEntity<Description> saveDescription(@RequestBody Description description){
        descriptionService.saveDescription(description);

        // Convert the Description model to a DescriptionDTO using ModelMapper
        DescriptionDTO dto = modelMapper.map(description, DescriptionDTO.class);
        producerService.sendDescription(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(description);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Description> updateDescription(@PathVariable("id") Long id,
                                                         @RequestBody Description description){
        descriptionService.updateDescription(id, description);

        //Convert the Description model to a DTO, using ModelMapper
        DescriptionDTO dto = modelMapper.map(description, DescriptionDTO.class);
        producerUpdate.updateDescription(dto.getTitle(), dto);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(description);
    }

    @DeleteMapping("/{id}")
    public String deleteDescription(@PathVariable("id") Long id){
        //Convert the Description model to a DTO, using ModelMapper
        Description description = descriptionService.getById(id);
        DescriptionDTO dto = modelMapper.map(description, DescriptionDTO.class);
        producerDelete.deleteDescription(dto.getTitle(), dto);

        descriptionService.deleteDescription(id);

        return "Description was deleted.";
    }
}
