package com.fpt.edu.schedule.ai.model;

import lombok.Data;

@Data
public class GaParameter {

    private int populationSize;

    private double mutationRate;

    private double tournamentSize;

    private int stepGeneration;
    private Cofficient cofficient;
    private int convergenceCheckRange;
    private int modelType;
}
