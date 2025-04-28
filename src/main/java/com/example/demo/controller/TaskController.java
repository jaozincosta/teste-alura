package com.example.demo.controller;

import com.example.demo.dto.OpenTextTaskDTO;
import com.example.demo.dto.SingleChoiceTaskDTO;
import com.example.demo.dto.MultipleChoiceTaskDTO;
import com.example.demo.model.Course;
import com.example.demo.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
@RequiredArgsConstructor
@Validated
public class TaskController {

    private final TaskService taskService;

    @PostMapping("/course/new")
    public ResponseEntity<Course> createCourse(@RequestBody @Valid Course course) {
        course.setStatus("BUILDING");
        course.setPublishedAt(null);
        Course savedCourse = taskService.saveCourse(course);
        return ResponseEntity.ok(savedCourse);
    }

    @PostMapping("/task/new/opentext")
    public ResponseEntity<String> createOpenTextTask(@RequestBody @Valid OpenTextTaskDTO dto) {
        taskService.createOpenTextTask(dto);
        return ResponseEntity.ok("Atividade de resposta aberta criada com sucesso!");
    }

    @PostMapping("/task/new/singlechoice")
    public ResponseEntity<String> createSingleChoiceTask(@RequestBody @Valid SingleChoiceTaskDTO dto) {
        taskService.createSingleChoiceTask(dto);
        return ResponseEntity.ok("Atividade de alternativa única criada com sucesso!");
    }

    @PostMapping("/task/new/multiplechoice")
    public ResponseEntity<String> createMultipleChoiceTask(@RequestBody @Valid MultipleChoiceTaskDTO dto) {
        taskService.createMultipleChoiceTask(dto);
        return ResponseEntity.ok("Atividade de múltipla escolha criada com sucesso!");
    }

    @PostMapping("/course/{id}/publish")
    public ResponseEntity<String> publishCourse(@PathVariable Long id) {
        taskService.publishCourse(id);
        return ResponseEntity.ok("Curso publicado com sucesso!");
    }
}
