package com.example.demo.dto;

import java.time.LocalDateTime;

public class CourseResponseDTO {
    private Long id;
    private String name;
    private String status;
    private LocalDateTime publishedAt;

    public CourseResponseDTO(Long id, String name, String status, LocalDateTime publishedAt) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.publishedAt = publishedAt;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getStatus() {
        return status;
    }

    public LocalDateTime getPublishedAt() {
        return publishedAt;
    }
}
