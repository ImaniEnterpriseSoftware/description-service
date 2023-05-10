package com.example.descriptioncud.controller;

import com.example.descriptioncud.model.Description;
import com.example.descriptioncud.service.DescriptionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/descriptions")
public class DescriptionController {

    private final DescriptionService descriptionService;

    public DescriptionController(DescriptionService descriptionService) {
        this.descriptionService = descriptionService;
    }

    @GetMapping
    public List<Description> getAll(){
        return descriptionService.getAllDescriptions();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Description> getById(@PathVariable Long id){
        Description description = descriptionService.getById(id);

        return ResponseEntity.ok(description);
    }

    @PostMapping
    public ResponseEntity<Description> saveDescription(@RequestBody Description description){
        descriptionService.saveDescription(description);

        return ResponseEntity.status(HttpStatus.CREATED).body(description);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Description> updateDescription(@PathVariable("id") Long id,
                                                         @RequestBody Description description){
        descriptionService.updateDescription(id, description);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(description);
    }

    @DeleteMapping("/{id}")
    public String deleteDescription(@PathVariable("id") Long id){
        descriptionService.deleteDescription(id);

        return "Description was deleted.";
    }
}
