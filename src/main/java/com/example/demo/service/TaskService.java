package com.example.demo.service;

import com.example.demo.dto.OpenTextTaskDTO;
import com.example.demo.dto.SingleChoiceTaskDTO;
import com.example.demo.dto.MultipleChoiceTaskDTO;
import com.example.demo.model.Course;
import com.example.demo.model.Task;
import com.example.demo.model.OptionTask;
import com.example.demo.repository.CourseRepository;
import com.example.demo.repository.TaskRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final CourseRepository courseRepository;

    @Transactional
    public Course saveCourse(Course course) {
        return courseRepository.save(course);
    }

    private void validateStatement(String statement) {
        if (statement == null || statement.length() < 4 || statement.length() > 255) {
            throw new IllegalArgumentException("O enunciado (statement) deve ter entre 4 e 255 caracteres.");
        }
    }

    private void validateDuplicateStatement(Long courseId, String statement) {
        List<Task> tasks = taskRepository.findByCourseId(courseId);

        boolean exists = tasks.stream()
                .anyMatch(task -> task.getStatement().equalsIgnoreCase(statement));

        if (exists) {
            throw new IllegalArgumentException("Já existe uma atividade com esse enunciado para este curso.");
        }
    }

    @Transactional
    public void createOpenTextTask(OpenTextTaskDTO dto) {
        validateStatement(dto.getStatement());
        validateDuplicateStatement(dto.getCourseId(), dto.getStatement());

        Task task = new Task();
        task.setStatement(dto.getStatement());
        task.setActivityType("OPEN_TEXT");
        task.setOrderNumber(dto.getOrder());

        Course course = new Course();
        course.setId(dto.getCourseId());
        task.setCourse(course);

        taskRepository.save(task);
    }

    @Transactional
    public void createSingleChoiceTask(SingleChoiceTaskDTO dto) {
        validateStatement(dto.getStatement());
        validateDuplicateStatement(dto.getCourseId(), dto.getStatement());

        Task task = new Task();
        task.setStatement(dto.getStatement());
        task.setActivityType("SINGLE_CHOICE");
        task.setOrderNumber(dto.getOrder());

        Course course = new Course();
        course.setId(dto.getCourseId());
        task.setCourse(course);

        List<OptionTask> options = dto.getOptions().stream().map(optionDTO -> {
            OptionTask option = new OptionTask();
            option.setOptionText(optionDTO.getOption());
            option.setIsCorrect(optionDTO.isCorrect());
            option.setTask(task);
            return option;
        }).toList();

        task.setOptions(options);

        taskRepository.save(task);
    }

    @Transactional
    public void createMultipleChoiceTask(MultipleChoiceTaskDTO dto) {
        validateStatement(dto.getStatement());
        validateDuplicateStatement(dto.getCourseId(), dto.getStatement());

        Task task = new Task();
        task.setStatement(dto.getStatement());
        task.setActivityType("MULTIPLE_CHOICE");
        task.setOrderNumber(dto.getOrder());

        Course course = new Course();
        course.setId(dto.getCourseId());
        task.setCourse(course);

        List<OptionTask> options = dto.getOptions().stream().map(optionDTO -> {
            OptionTask option = new OptionTask();
            option.setOptionText(optionDTO.getOption());
            option.setIsCorrect(optionDTO.isCorrect());
            option.setTask(task);
            return option;
        }).toList();

        task.setOptions(options);

        taskRepository.save(task);
    }
}
