package com.example.descriptioncud.service;

import com.example.descriptioncud.model.Description;
import com.example.descriptioncud.repository.DescriptionRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DescriptionService {

    private final DescriptionRepo descriptionRepo;

    public DescriptionService(DescriptionRepo descriptionRepo) {
        this.descriptionRepo = descriptionRepo;
    }

    public List<Description> getAllDescriptions() {
        return descriptionRepo.findAll();
    }

    public Description getById(Long id) {
        Description description = descriptionRepo.findById(id).get();
        return description;
    }

    public Description saveDescription(Description description) {
        return descriptionRepo.save(description);
    }

    public Description updateDescription(Long id, Description description) {
        Description descriptionToUpdate = descriptionRepo.findById(id).get();

        descriptionToUpdate.setDetails(description.getDetails());
        descriptionToUpdate.setCollection_id(description.getCollection_id());
        descriptionToUpdate.setTitle(description.getTitle());

        descriptionRepo.save(descriptionToUpdate);
        return description;
    }

    public void deleteDescription(Long id) {
        descriptionRepo.deleteById(id);
    }
}
