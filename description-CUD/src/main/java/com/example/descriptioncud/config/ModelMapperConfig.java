package com.example.descriptioncud.config;

import com.example.descriptioncud.model.Description;
import com.example.descriptioncud.model.DescriptionDTO;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

}
