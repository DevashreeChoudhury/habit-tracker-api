package com.example.habit.dto;

import lombok.Data;

@Data
public class TaskStatsDTO {

    private double completionPercentage;

    private int activeHabits;

    private int longestStreak;
}