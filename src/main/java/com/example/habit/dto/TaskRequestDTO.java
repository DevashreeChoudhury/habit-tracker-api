package com.example.habit.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TaskRequestDTO {

    @NotBlank(message = "Task name cannot be empty")
    private String taskName;
}
