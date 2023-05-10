package com.example.descriptioncud.repository;

import com.example.descriptioncud.model.Description;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DescriptionRepo extends JpaRepository<Description, Long> {
}
