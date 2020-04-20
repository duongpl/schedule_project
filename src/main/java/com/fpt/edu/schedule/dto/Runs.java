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
public class Runs {
    double bestFitness;
    double avgfitness;
    double numberOfViolation;
    double std;
    int generation;
    int id;
    List<TimetableEdit> timetableEdit;
    @JsonIgnore
    List<TimetableDetail> timetableDetails;


}
