package com.fpt.edu.schedule.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class Runs implements Comparable<Runs>  {
    double bestFitness;
    double avgFitness;
    double numberOfViolation;
    double std;
    int generation;
    int id;
    List<TimetableEdit> timetableEdit;


    @Override
    public int compareTo(Runs o) {
        return Integer.compare(this.getId(),o.getId());
    }
}
