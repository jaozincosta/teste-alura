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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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

    @Transactional
    public void createOpenTextTask(OpenTextTaskDTO dto) {
        validateNewTask(dto.getCourseId(), dto.getOrder());

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
        validateNewTask(dto.getCourseId(), dto.getOrder());
        validateSingleChoiceOptions(dto.getOptions(), dto.getStatement());

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
        validateNewTask(dto.getCourseId(), dto.getOrder());
        validateMultipleChoiceOptions(dto.getOptions(), dto.getStatement());

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

    @Transactional
    public void publishCourse(Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Curso não encontrado com ID: " + id));

        List<Task> tasks = taskRepository.findAllByCourseIdOrderByOrderNumber(id);

        if (tasks.isEmpty()) {
            throw new IllegalStateException("O curso precisa ter pelo menos uma atividade para ser publicado.");
        }

        boolean hasOpenText = tasks.stream().anyMatch(task -> task.getActivityType().equals("OPEN_TEXT"));
        boolean hasSingleChoice = tasks.stream().anyMatch(task -> task.getActivityType().equals("SINGLE_CHOICE"));
        boolean hasMultipleChoice = tasks.stream().anyMatch(task -> task.getActivityType().equals("MULTIPLE_CHOICE"));

        if (!(hasOpenText && hasSingleChoice && hasMultipleChoice)) {
            throw new IllegalStateException("O curso deve ter pelo menos uma atividade de cada tipo.");
        }

        for (int i = 0; i < tasks.size(); i++) {
            if (tasks.get(i).getOrderNumber() != (i + 1)) {
                throw new IllegalStateException("A sequência de ordem das atividades está incorreta.");
            }
        }

        course.setStatus("PUBLISHED");
        course.setPublishedAt(LocalDateTime.now());
        courseRepository.save(course);
    }


    private void validateNewTask(Long courseId, int newOrder) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("Curso não encontrado."));

        if (!"BUILDING".equals(course.getStatus())) {
            throw new IllegalStateException("O curso não está no status BUILDING.");
        }

        List<Task> tasks = taskRepository.findAllByCourseIdOrderByOrderNumber(courseId);

        if (!tasks.isEmpty()) {
            if (newOrder > tasks.size() + 1) {
                throw new IllegalStateException("Não é possível pular ordens na sequência de atividades.");
            }
        }

        tasks.stream()
                .filter(task -> task.getOrderNumber() >= newOrder)
                .forEach(task -> task.setOrderNumber(task.getOrderNumber() + 1));

        taskRepository.saveAll(tasks);
    }

    private void validateSingleChoiceOptions(List<OptionDTO> options, String statement) {
        if (options.size() < 2 || options.size() > 5) {
            throw new IllegalArgumentException("Atividade de alternativa única deve ter entre 2 e 5 opções.");
        }

        long correctCount = options.stream().filter(OptionDTO::isCorrect).count();
        if (correctCount != 1) {
            throw new IllegalArgumentException("Deve haver exatamente uma opção correta.");
        }

        validateOptionsCommonRules(options, statement);
    }

    private void validateMultipleChoiceOptions(List<OptionDTO> options, String statement) {
        if (options.size() < 3 || options.size() > 5) {
            throw new IllegalArgumentException("Atividade de múltipla escolha deve ter entre 3 e 5 opções.");
        }

        long correctCount = options.stream().filter(OptionDTO::isCorrect).count();
        if (correctCount < 2) {
            throw new IllegalArgumentException("Deve haver pelo menos duas opções corretas.");
        }

        if (correctCount == options.size()) {
            throw new IllegalArgumentException("Deve haver pelo menos uma opção incorreta.");
        }

        validateOptionsCommonRules(options, statement);
    }

    private void validateOptionsCommonRules(List<OptionDTO> options, String statement) {
        for (OptionDTO option : options) {
            if (option.getOption().length() < 4 || option.getOption().length() > 80) {
                throw new IllegalArgumentException("Cada opção deve ter entre 4 e 80 caracteres.");
            }

            if (option.getOption().equalsIgnoreCase(statement)) {
                throw new IllegalArgumentException("Nenhuma opção pode ser igual ao enunciado.");
            }
        }

        long distinctCount = options.stream()
                .map(OptionDTO::getOption)
                .distinct()
                .count();

        if (distinctCount != options.size()) {
            throw new IllegalArgumentException("Não pode haver opções duplicadas.");
        }
    }
}
