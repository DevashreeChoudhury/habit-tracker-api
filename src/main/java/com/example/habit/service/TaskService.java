package com.example.habit.service;

import com.example.habit.model.Task;

import java.util.List;

public interface TaskService {
    List<Task> getallTasks();
    Boolean createTask(Task task);

    String deleteTask(Long taskId);

    Task updateTask(Task task, Long taskId);

    double getCompletionPercentage();

    List<Task> getActiveHabits();

    Integer getLongestStreak();
}
