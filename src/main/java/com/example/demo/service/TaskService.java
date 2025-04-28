package com.example.demo.service;

import com.example.demo.dto.MultipleChoiceTaskDTO;
import com.example.demo.dto.OpenTextTaskDTO;
import com.example.demo.dto.OptionDTO;
import com.example.demo.dto.SingleChoiceTaskDTO;
import com.example.demo.model.Course;
import com.example.demo.model.OptionTask;
import com.example.demo.model.Task;
import com.example.demo.repository.CourseRepository;
import com.example.demo.repository.TaskRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final CourseRepository courseRepository;

    public TaskService(TaskRepository taskRepository, CourseRepository courseRepository) {
        this.taskRepository = taskRepository;
        this.courseRepository = courseRepository;
    }

    @Transactional
    public Course saveCourse(Course course) {
        return courseRepository.save(course);
    }

    @Transactional
    public void createOpenTextTask(OpenTextTaskDTO dto) {
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
        Task task = new Task();
        task.setStatement(dto.getStatement());
        task.setActivityType("SINGLE_CHOICE");
        task.setOrderNumber(dto.getOrder());

        Course course = new Course();
        course.setId(dto.getCourseId());
        task.setCourse(course);

        List<OptionTask> options = dto.getOptions().stream().map(this::mapOptionDTOToOptionTask).collect(Collectors.toList());
        task.setOptions(options);

        taskRepository.save(task);
    }

    @Transactional
    public void createMultipleChoiceTask(MultipleChoiceTaskDTO dto) {
        Task task = new Task();
        task.setStatement(dto.getStatement());
        task.setActivityType("MULTIPLE_CHOICE");
        task.setOrderNumber(dto.getOrder());

        Course course = new Course();
        course.setId(dto.getCourseId());
        task.setCourse(course);

        List<OptionTask> options = dto.getOptions().stream().map(this::mapOptionDTOToOptionTask).collect(Collectors.toList());
        task.setOptions(options);

        taskRepository.save(task);
    }

    private OptionTask mapOptionDTOToOptionTask(OptionDTO dto) {
        OptionTask optionTask = new OptionTask();
        optionTask.setOptionText(dto.getOption());
        optionTask.setIsCorrect(dto.isCorrect());
        return optionTask;
    }
}
