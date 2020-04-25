package com.fpt.edu.schedule.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fpt.edu.schedule.model.TimetableDetail;
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
    @JsonIgnore
    List<TimetableDetail> timetableDetails;


    @Override
    public int compareTo(Runs o) {
        return Integer.compare(this.getId(),o.getId());
    }
}
