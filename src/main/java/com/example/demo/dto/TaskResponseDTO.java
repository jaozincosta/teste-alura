package com.example.demo.dto;

public class TaskResponseDTO {

    private Long id;
    private String statement;
    private String activityType;
    private Integer orderNumber;

    public TaskResponseDTO() {}

    public TaskResponseDTO(Long id, String statement, String activityType, Integer orderNumber) {
        this.id = id;
        this.statement = statement;
        this.activityType = activityType;
        this.orderNumber = orderNumber;
    }

    public Integer getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Integer orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getActivityType() {
        return activityType;
    }

    public void setActivityType(String activityType) {
        this.activityType = activityType;
    }

    public String getStatement() {
        return statement;
    }

    public void setStatement(String statement) {
        this.statement = statement;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
