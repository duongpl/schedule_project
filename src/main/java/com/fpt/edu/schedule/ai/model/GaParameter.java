package com.fpt.edu.schedule.ai.model;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Data
public class GaParameter {
    @Min(value = 150)
    private int populationSize;
    @Max(value =1)
    private double mutationRate;
    @Min(value = 1)
    private double tournamentSize;
    private Cofficient cofficient;
    private int convergenceCheckRange;
}
