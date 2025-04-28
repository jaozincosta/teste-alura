package com.example.demo.service;

import com.example.demo.dto.CourseResponseDTO;
import com.example.demo.dto.TaskResponseDTO;
import com.example.demo.model.Course;
import com.example.demo.model.Task;
import com.example.demo.repository.CourseRepository;
import com.example.demo.repository.TaskRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;
    private final TaskRepository taskRepository;

    @Transactional
    public void publishCourse(Long courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("Curso não encontrado."));

        if (!"BUILDING".equals(course.getStatus())) {
            throw new IllegalStateException("Só é possível publicar cursos com status BUILDING.");
        }

        List<Task> tasks = taskRepository.findByCourseIdOrderByOrderNumberAsc(courseId);

        if (tasks.isEmpty()) {
            throw new IllegalStateException("O curso precisa ter atividades para ser publicado.");
        }

        validateContinuousOrder(tasks);
        validateActivityTypes(tasks);

        course.setStatus("PUBLISHED");
        course.setPublishedAt(LocalDateTime.now());

        courseRepository.save(course);
    }

    public List<CourseResponseDTO> listAllCourses() {
        return courseRepository.findAll()
                .stream()
                .map(course -> new CourseResponseDTO(
                        course.getId(),
                        course.getName(),
                        course.getStatus(),
                        course.getPublishedAt()
                ))
                .collect(Collectors.toList());
    }

    public List<TaskResponseDTO> getTasksByCourseId(Long courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("Curso não encontrado."));

        if (!"PUBLISHED".equals(course.getStatus())) {
            throw new IllegalStateException("Curso ainda não está publicado.");
        }

        List<Task> tasks = taskRepository.findByCourseIdOrderByOrderNumberAsc(courseId);

        return tasks.stream()
                .map(task -> new TaskResponseDTO(
                        task.getId(),
                        task.getStatement(),
                        task.getActivityType(),
                        task.getOrderNumber()
                ))
                .collect(Collectors.toList());
    }

    private void validateContinuousOrder(List<Task> tasks) {
        for (int i = 0; i < tasks.size(); i++) {
            if (!tasks.get(i).getOrderNumber().equals(i + 1)) {
                throw new IllegalStateException("As atividades devem ter ordens contínuas (1, 2, 3...).");
            }
        }
    }

    private void validateActivityTypes(List<Task> tasks) {
        boolean hasOpenText = false;
        boolean hasSingleChoice = false;
        boolean hasMultipleChoice = false;

        for (Task task : tasks) {
            switch (task.getActivityType()) {
                case "OPEN_TEXT" -> hasOpenText = true;
                case "SINGLE_CHOICE" -> hasSingleChoice = true;
                case "MULTIPLE_CHOICE" -> hasMultipleChoice = true;
            }
        }

        if (!hasOpenText || !hasSingleChoice || !hasMultipleChoice) {
            throw new IllegalStateException("O curso deve ter pelo menos uma atividade de cada tipo: OPEN_TEXT, SINGLE_CHOICE e MULTIPLE_CHOICE.");
        }
    }
}
