package com.fpt.edu.schedule.ai.model;

import lombok.Data;

@Data
public class Cofficient {
    private double hardConstraintCoff;
    private double softConstraintCoff;
    private double fulltimeCoff;
    private double parttimeCoff;
    private double slotCoff;
    private double subjectCoff;
    private double numberOfClassCoff;
    private double stdCoff;
    private double satisfactionSumCoff;
    private double distanceCoff;
    private double consicutiveClassCoff;
}
