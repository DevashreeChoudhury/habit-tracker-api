package com.example.habit.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import jakarta.validation.constraints.NotBlank;

@Entity(name="habits")
@Data
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long taskId;
    @NotBlank(message = "Task name cannot be empty")
    private String taskName;
    private Boolean taskCom;
    private Integer daysCom;

    public Task(Long taskId, String taskName, Boolean taskCom, Integer daysCom) {
        this.taskId = taskId;
        this.taskName = taskName;
        this.taskCom = taskCom;
        this.daysCom = daysCom;
    }

    public Task() {
    }
}
