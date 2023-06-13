package com.example.descriptioncud.tests;

import com.example.descriptioncud.controller.DescriptionController;
import com.example.descriptioncud.model.Description;
import com.example.descriptioncud.model.DescriptionDTO;
import com.example.descriptioncud.rabbitmq.ProducerDelete;
import com.example.descriptioncud.rabbitmq.ProducerService;
import com.example.descriptioncud.rabbitmq.ProducerUpdate;
import com.example.descriptioncud.service.DescriptionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class IntegrationTests {
    @Mock
    private DescriptionService descriptionService;
    @Mock
    private ProducerService producerService;
    @Mock
    private ModelMapper modelMapper;
    @Mock
    private ProducerUpdate producerUpdate;
    @Mock
    private ProducerDelete producerDelete;

    private DescriptionController descriptionController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        descriptionController = new DescriptionController(
                descriptionService, producerService, modelMapper, producerUpdate, producerDelete);
    }

    @Test
    void testGetAllDescriptions() {
        //Arrange
        List<Description> descriptionList = new ArrayList<>();
        descriptionList.add(new Description());
        descriptionList.add(new Description());

        when(descriptionService.getAllDescriptions()).thenReturn(descriptionList);

        //Act
        List<Description> result = descriptionController.getAll();

        verify(descriptionService, times(1)).getAllDescriptions();

        //Assert
        assertEquals(descriptionList, result);
    }

    @Test
    void testSaveDescription() {
        //Arrange
        Description description = new Description();
        when(descriptionService.saveDescription(description)).thenReturn(description);

        DescriptionDTO dto = new DescriptionDTO();
        when(modelMapper.map(description, DescriptionDTO.class)).thenReturn(dto);

        //Act
        ResponseEntity<Description> responseEntity = descriptionController.saveDescription(description);

        verify(descriptionService, times(1)).saveDescription(description);
        verify(modelMapper, times(1)).map(description, DescriptionDTO.class);
        verify(producerService, times(1)).sendDescription(dto);

        //Assert
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(description, responseEntity.getBody());
    }

    @Test
    void testUpdateDescription() {
        //Arrange
        Description description = new Description();
        Long id = 1L;

        when(descriptionService.updateDescription(id, description)).thenReturn(description);

        DescriptionDTO dto = new DescriptionDTO();
        when(modelMapper.map(description, DescriptionDTO.class)).thenReturn(dto);

        //Act
        ResponseEntity<Description> responseEntity = descriptionController.updateDescription(id, description);

        verify(descriptionService, times(1)).updateDescription(id, description);
        verify(modelMapper, times(1)).map(description, DescriptionDTO.class);
        verify(producerUpdate, times(1)).updateDescription(dto.getTitle(), dto);

        //Assert
        assertEquals(HttpStatus.ACCEPTED, responseEntity.getStatusCode());
        assertEquals(description, responseEntity.getBody());
    }

    @Test
    void testDeleteDescription() {
        //Arrange
        Description description = new Description();
        Long id = 1L;

        when(descriptionService.getById(id)).thenReturn(description);

        DescriptionDTO dto = new DescriptionDTO();
        when(modelMapper.map(description, DescriptionDTO.class)).thenReturn(dto);

        //Act
        String result = descriptionController.deleteDescription(id);

        verify(descriptionService, times(1)).getById(id);
        verify(modelMapper, times(1)).map(description, DescriptionDTO.class);
        verify(producerDelete, times(1)).deleteDescription(dto.getTitle(), dto);
        verify(descriptionService, times(1)).deleteDescription(id);

        //Assert
        assertEquals("Description was deleted.", result);
    }
}
