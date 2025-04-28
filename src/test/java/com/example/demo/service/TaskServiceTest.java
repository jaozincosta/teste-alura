package com.example.demo.service;

import com.example.demo.dto.OptionDTO;
import com.example.demo.dto.SingleChoiceTaskDTO;
import com.example.demo.model.Task;
import com.example.demo.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createSingleChoiceTask_shouldSaveTask() {

        SingleChoiceTaskDTO dto = new SingleChoiceTaskDTO();
        dto.setCourseId(6L);
        dto.setStatement("Qual a linguagem usada?");
        dto.setOrder(2);

        OptionDTO option1 = new OptionDTO();
        option1.setOption("Java");
        option1.setIsCorrect(true);

        OptionDTO option2 = new OptionDTO();
        option2.setOption("Python");
        option2.setIsCorrect(false);

        OptionDTO option3 = new OptionDTO();
        option3.setOption("C++");
        option3.setIsCorrect(false);

        dto.setOptions(List.of(option1, option2, option3));

        taskService.createSingleChoiceTask(dto);

        verify(taskRepository, times(1)).save(any(Task.class));
    }
}
