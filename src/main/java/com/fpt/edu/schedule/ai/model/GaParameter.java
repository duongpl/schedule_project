package com.fpt.edu.schedule.ai.model;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class GaParameter {
    private int populationSize;
    private double mutationRate;
    private double tournamentSize;
    private Cofficient cofficient;
    private int convergenceCheckRange;
}
