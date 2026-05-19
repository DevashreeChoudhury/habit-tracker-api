package com.example.habit.service;

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

    public double getCompletionPercentage() {

        List<Task> tasks = taskRepo.findAll();

        if(tasks.isEmpty()) return 0;

        long completed = tasks.stream()
                .filter(Task::getTaskCom)
                .count();

        return ((double) completed / tasks.size()) * 100;
    }

    public List<Task> getActiveHabits() {

        return taskRepo.findAll()
                .stream()
                .filter(task -> !task.getTaskCom())
                .toList();
    }

    public Integer getLongestStreak() {

        return taskRepo.findAll()
                .stream()
                .map(Task::getDaysCom)
                .max(Integer::compareTo)
                .orElse(0);
    }
}
