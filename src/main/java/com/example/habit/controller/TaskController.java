package com.example.habit.controller;

import com.example.habit.dto.TaskRequestDTO;
import com.example.habit.model.Task;
import com.example.habit.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TaskController {
    @Autowired
    private TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }
    @RequestMapping(value="/public/tasks",method= RequestMethod.GET)
    //@GetMapping("/api/public/categories")
    public List<Task> getAllTasks() {
        return taskService.getallTasks();
    }

    @RequestMapping(value="/public/tasks",method= RequestMethod.POST)
    public String createTask(@Valid @RequestBody TaskRequestDTO dto) {
        Task task=new Task();
        task.setTaskName(dto.getTaskName());
        task.setTaskCom(false);
        task.setDaysCom(0);
        Boolean bool=taskService.createTask(task);
        if (bool) return "task created successfully";
        return "task already exists";

    }

    @DeleteMapping("/admin/tasks/{taskId}")
    public ResponseEntity<String> deleteTask(@PathVariable Long taskId) {
        try {
            String status = taskService.deleteTask(taskId);
            return new ResponseEntity<>(status, HttpStatus.OK);
        } catch(ResponseStatusException e) {
            return new ResponseEntity<>(e.getReason() , e.getStatusCode());
        }
    }

    @PutMapping("/public/tasks/{taskId}") //for updating
    public ResponseEntity<String> updateTask(@RequestBody Task task1,@PathVariable Long taskId) {
        try {
            Task task = taskService.updateTask(task1,taskId);
            return new ResponseEntity<>("Task with task name: "+ task.getTaskName()+ " updated", HttpStatus.OK);
        } catch(ResponseStatusException e) {
            return new ResponseEntity<>(e.getReason() , e.getStatusCode());
        }
    }

    @GetMapping("/tasks/completion-percentage")
    public double getCompletionPercentage() {
        return taskService.getCompletionPercentage();
    }

    @GetMapping("/tasks/active")
    public List<Task> getActiveHabits() {
        return taskService.getActiveHabits();
    }

    @GetMapping("/tasks/longest-streak")
    public Integer getLongestStreak() {
        return taskService.getLongestStreak();
    }
}
