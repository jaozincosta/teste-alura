package com.example.demo.controller;

import com.example.demo.dto.CourseResponseDTO;
import com.example.demo.dto.TaskResponseDTO;
import com.example.demo.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/course")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;

    @PostMapping("/{id}/publish")
    public ResponseEntity<String> publishCourse(@PathVariable Long id) {
        courseService.publishCourse(id);
        return ResponseEntity.ok("Curso publicado com sucesso!");
    }

    @GetMapping
    public ResponseEntity<List<CourseResponseDTO>> listAllCourses() {
        List<CourseResponseDTO> courses = courseService.listAllCourses();
        return ResponseEntity.ok(courses);
    }

    @GetMapping("/{id}/tasks")
    public ResponseEntity<List<TaskResponseDTO>> getTasksByCourse(@PathVariable Long id) {
        List<TaskResponseDTO> tasks = courseService.getTasksByCourseId(id);
        return ResponseEntity.ok(tasks);
    }
}
