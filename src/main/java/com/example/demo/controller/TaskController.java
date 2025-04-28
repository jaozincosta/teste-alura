package com.example.demo.controller;

import com.example.demo.dto.OpenTextTaskDTO;
import com.example.demo.dto.SingleChoiceTaskDTO;
import com.example.demo.dto.MultipleChoiceTaskDTO;
import com.example.demo.model.Course;
import com.example.demo.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Validated
public class TaskController {

    private final TaskService taskService;

    @PostMapping("/courses/new")
    public ResponseEntity<Course> createCourse(@RequestBody @Valid Course course) {
        course.setStatus("BUILDING"); // Status inicial obrigatório
        course.setPublishedAt(null);  // Ainda não publicado
        Course savedCourse = taskService.saveCourse(course);
        return ResponseEntity.ok(savedCourse);
    }

    @PostMapping("/tasks/new/opentext")
    public ResponseEntity<String> createOpenTextTask(@RequestBody @Valid OpenTextTaskDTO dto) {
        taskService.createOpenTextTask(dto);
        return ResponseEntity.ok("Atividade de resposta aberta criada com sucesso!");
    }

    @PostMapping("/tasks/new/singlechoice")
    public ResponseEntity<String> createSingleChoiceTask(@RequestBody @Valid SingleChoiceTaskDTO dto) {
        taskService.createSingleChoiceTask(dto);
        return ResponseEntity.ok("Atividade de alternativa única criada com sucesso!");
    }

    // Endpoint para criar atividade de múltipla escolha
    @PostMapping("/tasks/new/multiplechoice")
    public ResponseEntity<String> createMultipleChoiceTask(@RequestBody @Valid MultipleChoiceTaskDTO dto) {
        taskService.createMultipleChoiceTask(dto);
        return ResponseEntity.ok("Atividade de múltipla escolha criada com sucesso!");
    }
}
