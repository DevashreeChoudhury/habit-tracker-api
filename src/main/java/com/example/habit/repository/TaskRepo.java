package com.example.habit.repository;

import com.example.habit.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepo extends JpaRepository<Task,Long> {
    boolean existsByTaskName(String taskName); // Spring generates this automatically
}
