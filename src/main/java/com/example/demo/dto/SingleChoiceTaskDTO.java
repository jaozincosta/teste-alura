package com.example.demo.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public class SingleChoiceTaskDTO {

    @NotNull(message = "O ID do curso não pode ser nulo.")
    private Long courseId;

    @NotBlank(message = "O enunciado não pode ser vazio.")
    @Size(min = 5, max = 255, message = "O enunciado deve ter entre 5 e 255 caracteres.")
    private String statement;

    @NotNull(message = "A ordem da atividade não pode ser nula.")
    private Integer order;

    @Valid
    @NotEmpty(message = "A lista de opções não pode ser vazia.")
    private List<OptionDTO> options;

    // Getters e Setters
    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public String getStatement() {
        return statement;
    }

    public void setStatement(String statement) {
        this.statement = statement;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public List<OptionDTO> getOptions() {
        return options;
    }

    public void setOptions(List<OptionDTO> options) {
        this.options = options;
    }
}
