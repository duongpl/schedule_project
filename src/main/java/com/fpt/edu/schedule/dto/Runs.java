package com.fpt.edu.schedule.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class Runs {
    double bestFitness;
    double avgfitness;
    double numberOfViolation;
    double std;
    int generation;
    int id;
    List<TimetableEdit> timetableEdit;


}
