package com.example.demo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "option_task")
public class OptionTask {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "option_text", nullable = false, length = 80)
    private String optionText;

    @Column(name = "is_correct", nullable = false)
    private Boolean isCorrect;

    @ManyToOne
    @JoinColumn(name = "task_id", nullable = false)
    private Task task;

    // Getters e Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOptionText() {
        return optionText;
    }

    public void setOptionText(String optionText) {
        this.optionText = optionText;
    }

    public Boolean isCorrect() {
        return isCorrect;
    }

    public void setIsCorrect(Boolean isCorrect) {
        this.isCorrect = isCorrect;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }
}
