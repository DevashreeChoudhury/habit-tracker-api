package com.example.habit.service;

import com.example.habit.dto.TaskStatsDTO;
import com.example.habit.model.Task;
import com.example.habit.repository.TaskRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class TaskImp implements TaskService{
    @Autowired
    private TaskRepo taskRepo;
    @Override
    public List<Task> getallTasks() {
        return taskRepo.findAll();
    }

    public Boolean createTask(Task task) {
        if (taskRepo.existsByTaskName(task.getTaskName())) {
            return false; // duplicate name found
        }
        task.setTaskId(null);
        taskRepo.save(task);
        return true;
    }

    @Override
    public String deleteTask(Long taskId) {
        Optional<Task> task=taskRepo.findById(taskId);
        Task task1=task
            .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found"));
        taskRepo.delete(task1);
        return "task deleted successfully";
    }

    @Override
    public Task updateTask(Task task, Long taskId) {
        Optional<Task> task2=taskRepo.findById(taskId);
        Task task3=task2
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found"));
        task3.setTaskCom(task.getTaskCom());
        if (task.getTaskCom()) task3.setDaysCom(task3.getDaysCom()+1);
        else task3.setDaysCom(0);
        taskRepo.save(task3);
        return task3;
    }

    @Override
    public TaskStatsDTO getTaskStats() {
        List<Task> tasks = taskRepo.findAll();

        TaskStatsDTO stats = new TaskStatsDTO();

        // completion percentage
        long completed = tasks.stream()
                .filter(Task::getTaskCom)
                .count();

        double completionPercentage = 0;

        if (!tasks.isEmpty()) {
            completionPercentage =
                    ((double) completed / tasks.size()) * 100;
        }

        // active habits
        int activeHabits = (int) tasks.stream()
                .filter(task -> !task.getTaskCom())
                .count();

        // longest streak
        int longestStreak = tasks.stream()
                .map(Task::getDaysCom)
                .max(Integer::compareTo)
                .orElse(0);

        // set DTO values
        stats.setCompletionPercentage(completionPercentage);
        stats.setActiveHabits(activeHabits);
        stats.setLongestStreak(longestStreak);

        return stats;
    }
    }

